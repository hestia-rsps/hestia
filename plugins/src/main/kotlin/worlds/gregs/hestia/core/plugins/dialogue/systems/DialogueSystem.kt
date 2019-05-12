package worlds.gregs.hestia.core.plugins.dialogue.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import kotlinx.coroutines.runBlocking
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import worlds.gregs.hestia.api.client.update.components.DisplayName
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.artemis.events.ContinueDialogue
import worlds.gregs.hestia.core.plugins.dialogue.components.*
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.*
import worlds.gregs.hestia.core.plugins.queue.components.SuspendingQueue
import worlds.gregs.hestia.core.plugins.queue.systems.QueueSystem
import worlds.gregs.hestia.core.plugins.widget.components.frame.chat.DialogueBox
import worlds.gregs.hestia.core.plugins.widget.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.game.queue.SuspendingCoroutine
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentAnimation
import worlds.gregs.hestia.network.client.encoders.messages.WidgetHeadMob
import worlds.gregs.hestia.network.client.encoders.messages.WidgetHeadPlayer
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItem
import worlds.gregs.hestia.services.send
import kotlin.coroutines.EmptyCoroutineContext

@Wire(failOnNull = false)
class DialogueSystem : DialogueBase() {

    private val logger = LoggerFactory.getLogger(DialogueSystem::class.java)
    private val scripts = mutableMapOf<String, DialogueQueue>()

    private lateinit var suspendingQueueMapper: ComponentMapper<SuspendingQueue>
    private lateinit var dialogueBoxMapper: ComponentMapper<DialogueBox>
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var dialogueBoxSystem: DialogueBoxSystem
    private lateinit var es: EventSystem
    private var ui: UserInterface? = null

    override fun addDialogue(name: String, block: DialogueQueue) {
        scripts[name] = block
    }

    override fun startDialogue(entityId: Int, block: DialogueQueue) {
        val map = suspendingQueueMapper.get(entityId)

        //Check if replacing existing dialogue (shouldn't)
        val existing = map.queue.firstOrNull { it.ctx is DialogueContext }
        if (existing != null) {
            map.queue.remove(existing)
            logger.debug("Overriding dialogue: $existing $block $this")
        }

        //Start dialogue at the dialogue after base.
        val coroutine = SuspendingCoroutine(EmptyCoroutineContext, block, DialogueContext())
        map.queue.add(coroutine)

        //Send dialogue
        progressDialogue(entityId, coroutine, null)
    }

    override fun startDialogue(entityId: Int, name: String) {
        //Find base dialogue in the script
        val base = scripts[name] ?: return logger.debug("Could not find dialogue '$name' $this")

        startDialogue(entityId, base)
    }

    override fun stopDialogue(entityId: Int) {
        suspendingQueueMapper.get(entityId).queue.removeIf { it.ctx is DialogueContext }
        ui?.close(entityId, DialogueBox::class)
    }

    override fun hasDialogue(entityId: Int): Boolean {
        return ui?.contains(entityId, DialogueBoxSystem::class) ?: false || suspendingQueueMapper.get(entityId).queue.any { it.ctx is DialogueContext }
    }

    private fun progressDialogue(entityId: Int, coroutine: SuspendingCoroutine<DialogueContext>, option: Int?) {
        //Set choice if given an option
        (coroutine.ctx.dialogue as? OptionsDialogue)?.selected = option

        //Continue dialogue
        runBlocking {
            val context = coroutine.next()
            send(entityId, context.dialogue)
        }

        //Close dialogue if reached end of script
        if (coroutine.ended()) {
            stopDialogue(entityId)
            logger.debug("Dialogue finished $entityId $coroutine $this")
        }
    }

    private fun getCoroutine(entityId: Int): SuspendingCoroutine<DialogueContext>? {
        val map = suspendingQueueMapper.get(entityId)
        return map.queue.firstOrNull { it.ctx is DialogueContext } as? SuspendingCoroutine<DialogueContext>
    }

    private fun continueDialogue(entityId: Int, option: Int = 0) {
        val coroutine = getCoroutine(entityId) ?: return logger.debug("No coroutine found")
        progressDialogue(entityId, coroutine, option)
    }

    @Subscribe
    private fun event(event: ContinueDialogue) {
        val (entityId, interfaceId, buttonId, component) = event
        if (dialogueBoxMapper.has(entityId)) {
            //Check interfaces match
            val box = dialogueBoxMapper.get(entityId)
            if (box.id != interfaceId) {
                logger.debug("Invalid dialogue id: ${box.id} $interfaceId $entityId")
                dialogueBoxMapper.remove(entityId)
                return
            }

            val dialogue = getCoroutine(entityId) ?: return
            val context = dialogue.ctx as? DialogueContext ?: return

            val continueButton = when (val data = context.dialogue) {
                is EntityDialogue -> 3 + data.lines.size + 1
                is StatementDialogue -> data.lines.size + 1
                /*is StringEntryDialogue -> */
                else -> -1
            }

            if (continueButton != -1 && buttonId == continueButton) {
                continueDialogue(entityId)//Click here to continue
            } else {
                continueDialogue(entityId, buttonId)//Option selected
            }
        }
    }

    /**
     * Sends dialogue widget to the client
     */
    private fun send(entityId: Int, dialogue: Dialogue?) {
        val interfaceId = when (dialogue) {
            is EntityDialogue -> QueueSystem.ENTITY_ID + dialogue.lines.size - 1
            is StatementDialogue -> QueueSystem.STATEMENT_ID + dialogue.lines.size - 1
            is OptionsDialogue -> when (dialogue.lines.size) {
                2 -> QueueSystem.TWO_OPTIONS_ID
                3 -> QueueSystem.THREE_OPTIONS_ID
                4 -> QueueSystem.FOUR_OPTIONS_ID
                else -> QueueSystem.FIVE_OPTIONS_ID
            }
            else -> throw UnsupportedOperationException("Unsupported dialogue $dialogue")
        }

        val componentStart = when (dialogue) {
            is EntityDialogue -> 3
            is StatementDialogue -> 0
            is OptionsDialogue -> if (dialogue.lines.size == 3) 1 else 0
            else -> throw UnsupportedOperationException("Unsupported dialogue $dialogue")
        }

        dialogueBoxSystem.openChatInterface(entityId, interfaceId)

        //Title
        val title = (dialogue as? ItemDialogue)?.title ?: when (dialogue) {
            is ItemDialogue -> null//TODO ItemDefinitions
            is MobDialogue -> null//TODO MobDefinitions
            is OptionsDialogue -> "Select an Option"
            is PlayerDialogue -> displayNameMapper.get(entityId)?.name
            else -> null
        }
        es.send(entityId, WidgetComponentText(interfaceId, componentStart, title ?: ""))

        //Lines
        if (dialogue is LinesDialogue) {
            dialogue.lines.forEachIndexed { index, text ->
                es.send(entityId, WidgetComponentText(interfaceId, componentStart + index + 1, text))
            }
        }

        //Extras
        when (dialogue) {
            is ItemDialogue -> es.send(entityId, WidgetItem(interfaceId, 2, dialogue.item, -1))
            is MobDialogue -> {
                es.send(entityId, WidgetHeadMob(interfaceId, 2, dialogue.mob))
                es.send(entityId, WidgetComponentAnimation(interfaceId, 2, dialogue.animation))
            }
            is PlayerDialogue -> {
                es.send(entityId, WidgetHeadPlayer(interfaceId, 2))
                es.send(entityId, WidgetComponentAnimation(interfaceId, 2, dialogue.animation))
            }
        }
    }
}
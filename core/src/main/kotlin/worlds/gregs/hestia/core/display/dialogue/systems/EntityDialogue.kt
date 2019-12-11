package worlds.gregs.hestia.core.display.dialogue.systems

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.artemis.events.ContinueDialogue
import worlds.gregs.hestia.core.display.dialogue.components.LinesDialogue
import worlds.gregs.hestia.core.display.dialogue.systems.types.ItemDialogue
import worlds.gregs.hestia.core.display.dialogue.systems.types.MobDialogue
import worlds.gregs.hestia.core.display.dialogue.systems.types.PlayerDialogue
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentAnimation
import worlds.gregs.hestia.network.client.encoders.messages.WidgetHeadMob
import worlds.gregs.hestia.network.client.encoders.messages.WidgetHeadPlayer
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItem
import worlds.gregs.hestia.services.send

/**
 * For quickly identifying entity dialogues
 */
abstract class EntityDialogue : LinesDialogue()

@Wire(injectInherited = true)
class EntityDialogueSystem : DialogueBaseSystem() {

    private val logger = LoggerFactory.getLogger(EntityDialogueSystem::class.java)!!

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleDefer(event: ProcessDeferral) {
        val (entityId, dialogue) = event
        if(dialogue is EntityDialogue) {
            val interfaceId = ENTITY_ID + dialogue.lines.size - 1
            val title = getTitle(dialogue)
            send(entityId, interfaceId, 3, title, dialogue.lines)
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
            event.isCancelled = true
        }
    }

    internal fun getTitle(dialogue: EntityDialogue): String? {
        return dialogue.title ?: when (dialogue) {
            is ItemDialogue -> null//TODO ItemDefinitions
            is MobDialogue -> null//TODO MobDefinitions
            else -> null
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleContinue(event: ContinueDialogue) {
        val (entityId, _, buttonId, _) = event
        val dialogue = getDeferral(entityId) as? EntityDialogue ?: return

        //Verify button
        val continueButton = 3 + dialogue.lines.size + 1
        if(continueButton != -1 && buttonId != continueButton) {
            return logger.debug("Unexpected button press: $event")
        }
        //Continue
        taskQueue.resume(entityId)
        event.isCancelled = true
    }

}
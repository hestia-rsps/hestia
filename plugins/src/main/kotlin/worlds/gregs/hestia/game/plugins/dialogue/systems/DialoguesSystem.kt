package worlds.gregs.hestia.game.plugins.dialogue.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.dialogue.Dialogue
import worlds.gregs.hestia.game.dialogue.DialogueName
import worlds.gregs.hestia.game.dialogue.faces.DialogueAction
import worlds.gregs.hestia.game.dialogue.faces.DialogueLinear
import worlds.gregs.hestia.game.dialogue.faces.DialogueRedirect
import worlds.gregs.hestia.game.plugins.dialogue.components.DialogueStage
import worlds.gregs.hestia.game.plugins.dialogue.systems.dialogues.ManDialogue
import worlds.gregs.hestia.game.plugins.dialogue.systems.types.BaseDialogue
import worlds.gregs.hestia.game.plugins.dialogue.systems.types.OptionsDialogue
import worlds.gregs.hestia.game.plugins.widget.components.frame.chat.DialogueBox
import worlds.gregs.hestia.game.plugins.widget.systems.frame.chat.DialogueBoxSystem
import java.util.*

@Wire(failOnNull = false)
class DialoguesSystem : DialogueBase() {

    private lateinit var dialogueStageMapper: ComponentMapper<DialogueStage>
    private val logger = LoggerFactory.getLogger(DialoguesSystem::class.java)
    private val scripts = HashMap<String, List<Dialogue>>()

    private val factory = DialogueFactory()

    private var ui: UserInterface? = null

    init {
        val classes = listOf(ManDialogue())
        classes.forEach { plugin ->
            val annotations = plugin::class.java.getAnnotation(DialogueName::class.java) ?: return@forEach
            annotations.names.forEach { name ->
                scripts[name] = factory.dialogue(name, plugin.dialogue)
            }
        }
    }
    private var system: DialogueBoxSystem? = null
    private lateinit var dialogueBoxMapper: ComponentMapper<DialogueBox>

    override fun openChatInterface(entityId: Int, widgetId: Int) {
        val existed = dialogueBoxMapper.has(entityId)
        val dialogueBox = dialogueBoxMapper.create(entityId)
        dialogueBox.id = widgetId
        if(existed) {
            //Refresh
            system?.open(entityId)
        }
    }

    override fun startDialogue(entityId: Int, name: String) {
        //Find base dialogue in the script
        val base = scripts[name]?.asSequence()?.filterIsInstance<BaseDialogue>()?.firstOrNull()

        if (base?.next == null) {
            logger.debug("Could not find base dialogue for '$name' $this")
            return
        }

        //Check if replacing existing dialogue (shouldn't)
        if (dialogueStageMapper.has(entityId)) {
            val dialogueStage = dialogueStageMapper.get(entityId)
            logger.debug("Overriding dialogue: ${dialogueStage.name} stage: ${dialogueStage.stage} $this")
        }

        val dialogueStage = dialogueStageMapper.create(entityId)

        //Start dialogue at the dialogue after base.
        dialogueStage.stage = base.next!!.id
        dialogueStage.name = name

        continueDialogue(entityId, -1)
    }

    override fun stopDialogue(entityId: Int) {
        dialogueStageMapper.remove(entityId)
        ui?.close(entityId, DialogueBox::class)
//        closeInterface(DialogueBox::class, WidgetType.CHAT)
    }

    override fun hasDialogue(entityId: Int): Boolean {
        return ui?.contains(entityId, DialogueBoxSystem::class) ?: false || dialogueStageMapper.has(entityId)
    }

    override fun getDialogue(entityId: Int, action: Boolean, option: Int): Dialogue? {
        if(!dialogueStageMapper.has(entityId)) {
            return null
        }
        val dialogueStage = dialogueStageMapper.get(entityId)
        var stage = dialogueStage.stage
        val name = dialogueStage.name
        val script = scripts[name] ?: return null
        val init = stage
        var increment = option >= 0

        //Find the next dialogue to be used
        var dialogue: Dialogue? = null
        while (dialogue == null || dialogue is DialogueRedirect) {
            dialogue = script.firstOrNull { it.id == stage }

            //Close dialogue if reached end of script
            if (dialogue == null) {
                stopDialogue(entityId)
                return null
            }

            //Change stage if dialogue is a redirect
            if(dialogue is DialogueRedirect) {
                stage = dialogue.directId
            }

            //Handle changes
            when(dialogue) {
                //Execute if is an action dialogue
                is DialogueAction -> {
                    if(action) {
                        dialogue.action.invoke(world.getEntity(entityId))
                    }
                }
                //Change to choice if option selected
                is OptionsDialogue -> {
                    if(option > 0) {
                        val choice = dialogue.options[option - 1]
                        dialogue = choice.next
                        if (dialogue == null) {
                            logger.debug("Invalid dialogue option type: $name $stage $option $choice $entityId")
                        }

                        //We skip OptionDialogues here and go straight to the next dialogue to save time
                        stage = dialogue?.id ?: stage
                    }
                }
                //Continue to next dialogue or end
                is DialogueLinear -> {
                    if(increment) {
                        stage = dialogue.next?.id ?: -1
                        dialogue = null
                        increment = false
                    }
                }
            }
        }

        if(init != stage) {
            dialogueStage.stage = stage
        }

        return dialogue
    }

    override fun continueDialogue(entityId: Int, option: Int) {
        val dialogue = getDialogue(entityId, true, option) ?: return
        //Send dialogue
        dialogue.send(world.getEntity(entityId), this)
    }
}
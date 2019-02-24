package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.game.MessageHandlerSystem
import worlds.gregs.hestia.game.plugins.dialogue.systems.types.BaseEntityDialogue
import worlds.gregs.hestia.game.plugins.dialogue.systems.types.OptionsDialogue
import worlds.gregs.hestia.game.plugins.widget.components.frame.chat.DialogueBox
import worlds.gregs.hestia.network.game.decoders.messages.DialogueContinue

@Wire(failOnNull = false)
class DialogueContinueHandler : MessageHandlerSystem<DialogueContinue>() {
    private val logger = LoggerFactory.getLogger(DialogueContinueHandler::class.java)
    private lateinit var dialogueBoxMapper: ComponentMapper<DialogueBox>
    private var system: DialogueBase? = null

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: DialogueContinue) {
        val (hash, component) = message
        val interfaceId = hash shr 16
        var buttonId = hash and 0xFF

        if(dialogueBoxMapper.has(entityId)) {
            //Check interfaces match
            val box = dialogueBoxMapper.get(entityId)
            if(box.id != interfaceId) {
                logger.debug("Invalid dialogue id: ${box.id} $interfaceId $entityId")
                dialogueBoxMapper.remove(entityId)
                return
            }

            val dialogue = system?.getDialogue(entityId, false) ?: return

            //Exception for two-options pressing '1' key
            if(buttonId > 100) {
                buttonId -= 100
            }

            when(dialogue) {
                //Pressed "click here to continue"
                is BaseEntityDialogue -> {
                    if(buttonId == dialogue.getComponentStart() + dialogue.lines.size + 1) {
                        system?.continueDialogue(entityId)
                    }
                }
                //Choose option
                is OptionsDialogue -> system?.continueDialogue(entityId, buttonId)
            }
        }
    }

}
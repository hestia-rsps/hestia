package worlds.gregs.hestia.core.display.widget.logic.systems.frame.chat

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.display.widget.model.components.frame.chat.DialogueBox
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame

class DialogueBoxSystem : BaseFrame(DialogueBox::class) {

    private lateinit var dialogueBoxMapper: ComponentMapper<DialogueBox>
    private val logger = LoggerFactory.getLogger(DialogueBoxSystem::class.java)!!

    override fun getId(entityId: Int): Int {
        return if(dialogueBoxMapper.has(entityId)) {
            dialogueBoxMapper.get(entityId).id
        } else {
            0
        }
    }

    override fun getWindow(entityId: Int): Int? {
        return WINDOW_ID
    }

    override fun getIndex(entityId: Int): Int {
        return INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val WINDOW_ID = 752
        private const val INDEX = 13
    }

    fun openChatInterface(entityId: Int, widgetId: Int) {
        val existed = dialogueBoxMapper.has(entityId)
        val dialogueBox = dialogueBoxMapper.create(entityId)
        dialogueBox.id = widgetId
        if(existed) {
            //Refresh
            open(entityId)
        }
    }

    /**
     * Checks continue dialogue events match an existing interface
     * otherwise cancels them.
     */
    @Subscribe(priority = Int.MAX_VALUE)
    internal fun handleContinue(event: ContinueDialogue) {
        val (entityId, interfaceId, _, _) = event
        if (!dialogueBoxMapper.has(entityId)) {
            event.isCancelled = true
            return
        }

        //Check interfaces match
        val box = dialogueBoxMapper.get(entityId)
        if (box.id != interfaceId) {
            logger.debug("Invalid dialogue id: ${box.id} $interfaceId $entityId")
            dialogueBoxMapper.remove(entityId)
            event.isCancelled = true
            return
        }
    }

}
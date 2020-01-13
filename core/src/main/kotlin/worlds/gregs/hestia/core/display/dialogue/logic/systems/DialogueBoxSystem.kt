package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.display.dialogue.model.components.DialogueBox
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.display.window.api.Windows

class DialogueBoxSystem : PassiveSystem() {

    private lateinit var dialogueBoxMapper: ComponentMapper<DialogueBox>
    private val logger = LoggerFactory.getLogger(DialogueBoxSystem::class.java)!!

    private lateinit var windows: Windows

    fun openChat(entityId: Int, windowId: Int) {
        val existed = dialogueBoxMapper.has(entityId)
        val dialogueBox = dialogueBoxMapper.create(entityId)
        dialogueBox.id = windowId
        if(existed) {
            //Refresh
            windows.refreshWindow(entityId, windowId)
        }
    }

    /**
     * Checks continue dialogue events match an existing interface
     * otherwise cancels them.
     */
    @Subscribe(priority = Int.MAX_VALUE)
    internal fun handleContinue(event: ContinueDialogue) {
        /*val (entityId, window, _, _) = event
        if (!dialogueBoxMapper.has(entityId)) {
            event.isCancelled = true
            return
        }

        //Check interfaces match
        val box = dialogueBoxMapper.get(entityId)
        if (box.id != window) {
            logger.debug("Invalid dialogue id: ${box.id} $window $entityId")
            dialogueBoxMapper.remove(entityId)
            event.isCancelled = true
            return
        }*/
    }

    /**
     * Closes dialogue
     */

}
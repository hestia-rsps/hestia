package worlds.gregs.hestia.core.plugins.dialogue.systems.types

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.api.task.events.ProcessDeferral
import worlds.gregs.hestia.artemis.events.ContinueDialogue
import worlds.gregs.hestia.core.plugins.dialogue.components.LinesDialogue
import worlds.gregs.hestia.core.plugins.dialogue.systems.DialogueBaseSystem
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.services.wrap

data class StatementDialogue(override val lines: List<String>, override val title: String?) : LinesDialogue() {
    init {
        check(lines.size <= 5) { "Maximum statement dialogue lines 5" }
    }
}

suspend fun TaskScope.statement(text: String, title: String? = null) {
    deferral = StatementDialogue(text.wrap(), title)
    defer()
}

@Wire(injectInherited = true)
class StatementDialogueSystem : DialogueBaseSystem() {

    private val logger = LoggerFactory.getLogger(StatementDialogueSystem::class.java)!!

    @Subscribe
    private fun handleDefer(event: ProcessDeferral) {
        val (entityId, dialogue) = event
        if(dialogue is StatementDialogue) {
            val interfaceId = STATEMENT_ID + dialogue.lines.size - 1
            send(entityId, interfaceId, 0, dialogue.title, dialogue.lines)
            event.isCancelled = true
        }
    }

    @Subscribe
    private fun handleContinue(event: ContinueDialogue) {
        val (entityId, _, buttonId, _) = event
        val dialogue = getDeferral(entityId) as? StatementDialogue ?: return

        //Verify button
        val continueButton = dialogue.lines.size + 1
        if(continueButton != -1 && buttonId != continueButton) {
            return logger.debug("Unexpected button press: $event")
        }

        //Continue
        taskQueue.resume(entityId)
    }

}
package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import com.artemis.annotations.Wire
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.display.dialogue.api.LinesDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueBaseSystem
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

data class StatementDialogue(override val lines: List<String>, override val title: String?, override val continuation: CancellableContinuation<Unit>) : TaskType<Unit>, LinesDialogue() {
    init {
        check(lines.size <= 5) { "Maximum statement dialogue lines 5" }
    }
}

suspend fun Task.statement(text: String, title: String? = null) = suspendCancellableCoroutine<Unit> {
    suspension = StatementDialogue(text.trimIndent().lines(), title, it)
}

@Wire(injectInherited = true)
class StatementDialogueSystem : DialogueBaseSystem() {

    override val logger = LoggerFactory.getLogger(StatementDialogueSystem::class.java)!!

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspend(event: ProcessTaskSuspension) {
        val (entityId, dialogue) = event
        if (dialogue is StatementDialogue) {
            val interfaceId = STATEMENT_ID + dialogue.lines.size - 1
            send(entityId, interfaceId, 0, dialogue.title, dialogue.lines)
            event.isCancelled = true
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleContinue(event: ContinueDialogue) {
        val (entityId, _, buttonId, _) = event
        val suspension = tasks.getSuspension(entityId)
        if (suspension is StatementDialogue) {
            //Verify button
            val continueButton = suspension.lines.size + 1
            if (continueButton != -1 && buttonId != continueButton) {
                return logger.debug("Unexpected button press: $event")
            }

            //Continue
            tasks.resume(entityId, suspension, Unit)
            event.isCancelled = true
        }
    }

}
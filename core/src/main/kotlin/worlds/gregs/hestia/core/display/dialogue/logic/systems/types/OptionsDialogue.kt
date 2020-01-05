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

data class OptionsDialogue(override val lines: List<String>, override val title: String?, override val continuation: CancellableContinuation<Int>) : TaskType<Int>, LinesDialogue() {
    init {
        check(lines.size <= 5) { "Maximum option dialogue lines 5" }
        check(lines.size >= 2) { "Minimum option dialogue lines 2" }
    }
}

@Wire(injectInherited = true)
class OptionsDialogueSystem : DialogueBaseSystem() {

    override val logger = LoggerFactory.getLogger(OptionsDialogueSystem::class.java)!!

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspend(event: ProcessTaskSuspension) {
        val (entityId, dialogue) = event
        if(dialogue is OptionsDialogue) {
            val window = when (dialogue.lines.size) {
                2 -> TWO_OPTIONS_ID
                3 -> THREE_OPTIONS_ID
                4 -> FOUR_OPTIONS_ID
                else -> FIVE_OPTIONS_ID
            }
            val widgetStart = if (dialogue.lines.size == 3) 1 else 0
            val title = dialogue.title ?: "Select an Option"
            send(entityId, window, widgetStart, title, dialogue.lines)
            event.isCancelled = true
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleContinue(event: ContinueDialogue) {
        val (entityId, _, option, _) = event
        val suspension = tasks.getSuspension(entityId)
        if (suspension is OptionsDialogue) {
            val choice = option - if(suspension.lines.size == 3) 2 else 1
            //Continue
            if(tasks.resume(entityId, suspension, choice)) {
                event.isCancelled = true
            } else {
                logger.warn("Error resuming task suspension $suspension")
            }
        }
    }

}

suspend fun Task.options(builder: DialogueBuilder): Int {
    val options = builder.message.trimIndent().lines().filter { it.isNotBlank() }
    return option(builder.title, options)
}

/**
 * List of up to 5 options for the player to select from
 * @param title Optional title - Default "Select an Option"
 * @param options The options to select between (1-5)
 */
suspend fun Task.option(title: String? = null, options: List<String>) = suspendCancellableCoroutine<Int> {
    suspension = OptionsDialogue(options, title, it)
}
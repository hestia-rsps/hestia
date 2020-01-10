package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import com.artemis.annotations.Wire
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueBaseSystem
import worlds.gregs.hestia.core.display.dialogue.model.events.IntegerEntered
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.network.client.encoders.messages.Script

data class IntegerEntryDialogue(val title: String, override var continuation: CancellableContinuation<Int>) : TaskType<Int>, Dialogue

suspend fun Task.intEntry(title: String) = suspendCancellableCoroutine<Int> {
    suspension = IntegerEntryDialogue(title, it)
}

@Wire(injectInherited = true)
class IntegerEntryDialogueSystem : DialogueBaseSystem() {

    override val logger = LoggerFactory.getLogger(IntegerEntryDialogueSystem::class.java)!!

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspend(event: ProcessTaskSuspension) {
        val (entityId, dialogue) = event
        if(dialogue is IntegerEntryDialogue) {
            es.send(entityId, Script(109, dialogue.title))
            event.isCancelled = true
        }
    }

    @Subscribe
    private fun handleInput(event: IntegerEntered) {
        val (entityId, integer) = event
        val suspension = tasks.getSuspension(entityId)
        val dialogue = suspension as? IntegerEntryDialogue ?: return logger.debug("Task not expected $suspension $event")
        //Continue
        tasks.resume(entityId, dialogue, integer)
    }

}
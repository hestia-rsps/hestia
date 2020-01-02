package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import com.artemis.annotations.Wire
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueBaseSystem
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.network.client.encoders.messages.Script

data class StringEntryDialogue(val title: String, override val continuation: CancellableContinuation<String>) : TaskType<String>, Dialogue

suspend fun Task.stringEntry(title: String) = suspendCancellableCoroutine<String> {
    suspension = StringEntryDialogue(title, it)
}

@Wire(injectInherited = true)
class StringEntryDialogueSystem : DialogueBaseSystem() {

    override val logger = LoggerFactory.getLogger(StringEntryDialogueSystem::class.java)!!

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspend(event: ProcessTaskSuspension) {
        val (entityId, dialogue) = event
        if(dialogue is StringEntryDialogue) {
            es.send(entityId, Script(108, dialogue.title))
            event.isCancelled = true
        }
    }

    @Subscribe
    private fun handleInput(event: StringEntered) {
        val (entityId, text) = event
        val suspension = tasks.getSuspension(entityId)
        if (suspension is StringEntryDialogue) {
            //Continue
            tasks.resume(entityId, suspension, text)
        } else {
            logger.warn("Unexpected dialogue suspension task $suspension")
        }
    }

}
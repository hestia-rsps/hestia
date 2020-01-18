package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import com.artemis.annotations.Wire
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.network.client.encoders.messages.Script

data class StringEntryDialogue(val title: String, override var continuation: CancellableContinuation<String>) : TaskType<String>

suspend fun Task.stringEntry(title: String) = suspendCancellableCoroutine<String> {
    suspension = StringEntryDialogue(title, it)
}

@Wire(injectInherited = true)
class StringEntryDialogueSystem : PassiveSystem() {

    private lateinit var tasks: Tasks
    private lateinit var es: EventSystem
    private val logger = LoggerFactory.getLogger(StringEntryDialogueSystem::class.java)!!

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
        val (text) = event
        val suspension = tasks.getSuspension(event.entity)
        if (suspension is StringEntryDialogue) {
            //Continue
            tasks.resume(event.entity, suspension, text)
        } else {
            logger.warn("Unexpected dialogue suspension task $suspension")
        }
    }

}
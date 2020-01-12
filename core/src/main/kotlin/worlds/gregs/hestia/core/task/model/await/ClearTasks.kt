package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType

class ClearTasks : EntityAction(), TaskType<Unit>, InstantEvent {
    override lateinit var continuation: CancellableContinuation<Unit>
}

suspend fun Task.awaitClearTasks(es: EventSystem, entityId: Int) = suspendCancellableCoroutine<Unit> {
    val clear = ClearTasks()
    clear.continuation = it
    suspension = clear
    es.perform(entityId, clear)
}
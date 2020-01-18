package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskType

/**
 * @param id Interface id
 */
data class InterfaceClose(val id: Int) : TaskType<Unit> {
    override lateinit var continuation: CancellableContinuation<Unit>
}
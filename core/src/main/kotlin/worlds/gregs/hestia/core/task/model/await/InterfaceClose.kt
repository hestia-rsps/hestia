package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskSuspension

/**
 * @param id Interface id
 */
data class InterfaceClose(val id: Int) : TaskSuspension<Unit> {
    override lateinit var continuation: CancellableContinuation<Unit>
}
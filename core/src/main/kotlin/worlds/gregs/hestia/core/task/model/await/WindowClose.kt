package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskType

data class WindowClose(val window: Int) : TaskType<Unit> {
    override lateinit var continuation: CancellableContinuation<Unit>
}
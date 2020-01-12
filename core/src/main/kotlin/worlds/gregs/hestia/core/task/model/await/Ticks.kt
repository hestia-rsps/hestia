package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskType

data class Ticks(var ticks: Int) : TaskType<Unit> {
    override lateinit var continuation: CancellableContinuation<Unit>
}
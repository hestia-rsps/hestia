package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskType

data class WithinRange(val targetId: Int, val distance: Int) : TaskType<Boolean> {
    override lateinit var continuation: CancellableContinuation<Boolean>

    constructor(targetId: Int, distance: Int, continuation: CancellableContinuation<Boolean>) : this(targetId, distance) {
        this.continuation = continuation
    }
}
package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskSuspension

data class InteractionComplete(val targetId: Int) : TaskSuspension<Boolean> {
    override lateinit var continuation: CancellableContinuation<Boolean>

    constructor(targetId: Int, continuation: CancellableContinuation<Boolean>) : this(targetId) {
        this.continuation = continuation
    }
}
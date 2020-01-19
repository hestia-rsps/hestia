package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskSuspension

object Forever : TaskSuspension<Unit> {
    override lateinit var continuation: CancellableContinuation<Unit>
}
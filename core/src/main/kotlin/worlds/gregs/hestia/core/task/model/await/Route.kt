package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskType

class Route : TaskType<RouteResult> {
    lateinit var route: RouteResult
    override lateinit var continuation: CancellableContinuation<RouteResult>
}
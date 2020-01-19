package worlds.gregs.hestia.core.task.api

import kotlinx.coroutines.CancellableContinuation

interface TaskSuspension<T> {
    var continuation: CancellableContinuation<T>
}
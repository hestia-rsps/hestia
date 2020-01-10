package worlds.gregs.hestia.core.task.api

import kotlinx.coroutines.CancellableContinuation

interface TaskType<T> {
    var continuation: CancellableContinuation<T>
}
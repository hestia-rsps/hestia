package worlds.gregs.hestia.core.task.api

import kotlinx.coroutines.CancellableContinuation

interface TaskType<T> {
    val continuation: CancellableContinuation<T>
}
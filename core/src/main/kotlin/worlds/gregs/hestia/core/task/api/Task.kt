package worlds.gregs.hestia.core.task.api

import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

/**
 * A suspendable coroutine holding multiple actions or events to process
 */
interface Task : Continuation<Any> {

    override val context: CoroutineContext

    var suspension: TaskSuspension<*>?

    fun onCancel(handler: CompletionHandler)

    val isActive: Boolean
        get() = suspension?.continuation?.isActive ?: false

    val isCancelled: Boolean
        get() = suspension?.continuation?.isCancelled ?: false

    val isCompleted: Boolean
        get() = suspension?.continuation?.isCompleted ?: false

    fun cancel(cancellation: TaskCancellation) {
        throw cancellation
    }

    fun cancel(message: String) = cancel(TaskCancellation.Cancellation(message))

    suspend fun <T> await(type: TaskSuspension<T>) = suspendCancellableCoroutine<T> {
        type.continuation = it
        suspension = type
    }
}
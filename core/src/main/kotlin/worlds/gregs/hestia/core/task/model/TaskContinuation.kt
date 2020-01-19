package worlds.gregs.hestia.core.task.model

import kotlinx.coroutines.CompletionHandler
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.api.TaskSuspension
import kotlin.coroutines.CoroutineContext

class TaskContinuation(override val context: CoroutineContext) : Task {
    private var onCancel: CompletionHandler? = null
    override var suspension: TaskSuspension<*>? = null

    override fun onCancel(handler: CompletionHandler) {
        this.onCancel = handler
    }

    override fun resumeWith(result: Result<Any>) {
        if (result.isFailure) {
            val exception = result.exceptionOrNull()!!
            onCancel?.invoke(exception)
            if(exception !is TaskCancellation || exception is TaskCancellation.Cancellation) {
                exception.printStackTrace()
            }
        }
    }
}
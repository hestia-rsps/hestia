package worlds.gregs.hestia.core.task.model

import kotlinx.coroutines.CompletionHandler
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.api.TaskContext
import worlds.gregs.hestia.core.task.api.TaskType

class TaskContinuation(override val context: TaskContext) : Task {
    private var onCancel: CompletionHandler? = null
    override var suspension: TaskType<*>? = null

    override fun onCancel(handler: CompletionHandler) {
        this.onCancel = handler
    }

    override fun resumeWith(result: Result<Any>) {
        if (result.isFailure) {
            val exception = result.exceptionOrNull()!!
            if(exception is TaskCancellation && exception !is TaskCancellation.Cancellation) {
                onCancel?.invoke(exception)
            } else {
                exception.printStackTrace()
            }
        }
    }
}
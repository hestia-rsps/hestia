package worlds.gregs.hestia.core.task.api

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

abstract class TaskContext : AbstractCoroutineContextElement(TaskContext) {
    companion object Key : CoroutineContext.Key<TaskContext>
}
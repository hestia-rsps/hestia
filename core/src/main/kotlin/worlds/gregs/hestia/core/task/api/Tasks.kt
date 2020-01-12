package worlds.gregs.hestia.core.task.api

import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.components.TaskQueue

abstract class Tasks : IteratingSystem(Aspect.all(TaskQueue::class)) {

    abstract fun getSuspension(entityId: Int): TaskType<*>?

    abstract fun <T> resume(entityId: Int, type: TaskType<T>, result: T): Boolean

    abstract fun cancel(entityId: Int, cause: TaskCancellation)

    abstract fun cancelAll(entityId: Int, cause: TaskCancellation, priority: Int = -1)

    abstract fun activateTask(entityId: Int, task: InactiveTask)

}
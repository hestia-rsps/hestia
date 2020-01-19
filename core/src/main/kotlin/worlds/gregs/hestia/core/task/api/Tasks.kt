package worlds.gregs.hestia.core.task.api

import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.components.TaskQueue

abstract class Tasks : IteratingSystem(Aspect.all(TaskQueue::class)) {

    /**
     * Gets the current active task [TaskSuspension]
     * @param entityId The entity to get the active task of
     * @return The top priority active task type
     */
    abstract fun getSuspension(entityId: Int): TaskSuspension<*>?

    /**
     * Resumes the current task with [result]
     * @param entityId The entity who's task to resume
     * @param type The task type expected to continue
     * @param result The result to pass through ([Unit] if none)
     * @return whether the resuming was successful
     */
    abstract fun <T> resume(entityId: Int, type: TaskSuspension<T>, result: T): Boolean

    /**
     * Cancels the current highest priority task
     * @param entityId The entity who's top task to cancel
     * @param cause The reason for cancelling
     */
    abstract fun cancel(entityId: Int, cause: TaskCancellation)

    /**
     * Cancels all active tasks with or without a set priority
     * @param entityId The entity to cancel tasks of
     * @param cause The reason for cancelling
     * @param priority The priority of tasks to cancel (-1 for all priorities)
     */
    abstract fun cancelAll(entityId: Int, cause: TaskCancellation, priority: Int = -1)

    /**
     * Queues a task for activation
     * @param entityId The entity to queue the task with
     * @param task The inactive task to queue for activation
     */
    abstract fun activateTask(entityId: Int, task: InactiveTask)

}
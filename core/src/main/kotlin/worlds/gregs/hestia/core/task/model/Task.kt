package worlds.gregs.hestia.core.task.model

import worlds.gregs.hestia.game.task.DeferQueue
import worlds.gregs.hestia.game.task.TaskPriority

/**
 * A suspendable queue of actions to complete
 */
data class Task(val priority: TaskPriority, val queue: DeferQueue)
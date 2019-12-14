package worlds.gregs.hestia.core.task.model

import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.TaskPriority

data class ReusableTask(val priority: TaskPriority, val block: SuspendableQueue)
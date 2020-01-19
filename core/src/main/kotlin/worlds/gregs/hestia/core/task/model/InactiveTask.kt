package worlds.gregs.hestia.core.task.model

import worlds.gregs.hestia.core.task.api.SuspendableQueue

data class InactiveTask(val priority: Int, val task: SuspendableQueue)
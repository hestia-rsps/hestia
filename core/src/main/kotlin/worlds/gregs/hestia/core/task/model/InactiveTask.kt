package worlds.gregs.hestia.core.task.model

import worlds.gregs.hestia.core.task.api.SuspendableQueue

data class InactiveTask<T : Any>(val task: SuspendableQueue, val param: T? = null)
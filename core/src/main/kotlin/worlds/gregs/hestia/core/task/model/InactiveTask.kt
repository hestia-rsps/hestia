package worlds.gregs.hestia.core.task.model

data class InactiveTask<T : Any>(val task: ReusableTask, val param: T? = null)
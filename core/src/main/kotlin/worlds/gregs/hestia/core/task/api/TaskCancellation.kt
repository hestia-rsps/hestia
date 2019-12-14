package worlds.gregs.hestia.core.task.api

sealed class TaskCancellation(message: String) : Throwable(message, null) {
    class Cancellation(message: String) : TaskCancellation(message)
    object Priority : TaskCancellation("Cancelled by a higher priority task.")
    object Walk : TaskCancellation("Cancelled by standard walk to position.")
    object OutOfReach : TaskCancellation("Cancelled by unreachable target.")
}
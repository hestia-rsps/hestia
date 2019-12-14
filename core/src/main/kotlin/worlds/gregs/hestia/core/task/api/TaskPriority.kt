package worlds.gregs.hestia.core.task.api

sealed class TaskPriority {
    /**
     *  Waits for any opened screens to close before starting
     */
    object Low : TaskPriority()

    /**
     *  Starts on it's turn
     */
    object Normal : TaskPriority()

    /**
     * Stops all other tasks before starting
     */
    object High : TaskPriority()

}
package worlds.gregs.hestia.game.task

sealed class TaskPriority {
    /**
     *  Waits for any opened screens to close before starting
     */
    object Weak : TaskPriority()

    /**
     *  Starts on it's turn
     */
    object Normal : TaskPriority()

    /**
     * Stops all other tasks before starting
     */
    object Strong : TaskPriority()

}
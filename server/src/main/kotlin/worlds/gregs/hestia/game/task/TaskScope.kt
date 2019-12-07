package worlds.gregs.hestia.game.task

interface TaskScope {

    /**
     * Suspends the coroutine until [DeferringCoroutine.next] is called
     */
    suspend fun defer()

    /**
     * Stops the coroutine pre-maturely
     * @param block for disabling suspension when calling externally
     */
    suspend fun stop(block: Boolean = true)

    /**
     * Whether coroutine has finished
     * @return Whether the coroutine has computed the last step.
     */
    fun stopped(): Boolean

    /**
     * Resumes the suspension
     */
    suspend fun next(): DeferralType?

    /**
     * The current type of suspension waiting for resume
     */
    var deferral: DeferralType?

    /**
     * Priority of the task
     */
    val priority: TaskPriority

}
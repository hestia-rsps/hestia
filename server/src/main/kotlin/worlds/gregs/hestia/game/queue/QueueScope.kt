package worlds.gregs.hestia.game.queue

interface QueueScope<T : QueueContext> {

    /**
     * Suspends the coroutine until [SuspendingCoroutine.next] is called
     */
    suspend fun suspend()

    /**
     * Ends the coroutine pre-maturely
     */
    fun end()

    /**
     * Queue Context
     */
    val ctx: T

}
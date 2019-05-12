package worlds.gregs.hestia.core.plugins.queue.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import kotlinx.coroutines.runBlocking
import worlds.gregs.hestia.core.plugins.queue.components.SuspendingQueue
import worlds.gregs.hestia.game.queue.SuspendingCoroutine
import worlds.gregs.hestia.services.Aspect

/**
 *  Processes [SuspendingCoroutine] calling [process] and removing if [SuspendingCoroutine.ended]
 */
abstract class QueueSystem : IteratingSystem(Aspect.all(SuspendingQueue::class)) {

    private lateinit var suspendingQueueMapper: ComponentMapper<SuspendingQueue>

    abstract suspend fun process(entityId: Int, queue: List<SuspendingCoroutine<*>>)

    override fun process(entityId: Int) {
        val suspendingQueue = suspendingQueueMapper.get(entityId)
        runBlocking {
            process(entityId, suspendingQueue.queue)

            suspendingQueue.queue.removeAll { it.ended() }
        }
    }

    companion object {
        internal const val ENTITY_ID = 241
        internal const val STATEMENT_ID = 210
        internal const val TWO_OPTIONS_ID = 236
        internal const val THREE_OPTIONS_ID = 231
        internal const val FOUR_OPTIONS_ID = 237
        internal const val FIVE_OPTIONS_ID = 238
    }

}
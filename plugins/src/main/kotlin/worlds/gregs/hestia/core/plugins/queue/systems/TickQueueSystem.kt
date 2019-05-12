package worlds.gregs.hestia.core.plugins.queue.systems

import worlds.gregs.hestia.core.plugins.queue.components.TickContext
import worlds.gregs.hestia.game.queue.SuspendingCoroutine

/**
 *  Processes [SuspendingCoroutine] with [TickContext]
 *  Decreases each tick until equal to zero then suspension is resumed
 */
class TickQueueSystem : QueueSystem() {

    override suspend fun process(entityId: Int, queue: List<SuspendingCoroutine<*>>) {
        queue.filter { it.ctx is TickContext }.forEach {
            val context = it.ctx as TickContext
            context.ticks--
            if (context.ticks <= 0) {
                it.next()
            }
        }
    }

}
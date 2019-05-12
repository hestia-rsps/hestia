package worlds.gregs.hestia.core.plugins.queue.components

import worlds.gregs.hestia.game.queue.QueueContext
import worlds.gregs.hestia.game.queue.QueueScope

data class TickContext(var ticks: Int) : QueueContext

suspend fun QueueScope<TickContext>.wait(ticks: Int) {
    ctx.ticks = ticks
    suspend()
}
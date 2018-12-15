package worlds.gregs.hestia.game.plugins.mob.systems.chunk

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.mob.Mob
import worlds.gregs.hestia.api.movement.systems.ChunkSubscription
import worlds.gregs.hestia.game.events.MobChunkChanged

class MobChunkSubscriptionSystem : ChunkSubscription(Mob::class) {
    private lateinit var es: EventSystem

    override fun changedChunk(entityId: Int, from: Int?, to: Int?) {
        es.dispatch(MobChunkChanged(entityId, from, to))
    }
}
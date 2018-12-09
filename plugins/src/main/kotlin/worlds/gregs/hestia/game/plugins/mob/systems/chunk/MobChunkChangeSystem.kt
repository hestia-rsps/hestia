package worlds.gregs.hestia.game.plugins.mob.systems.chunk

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.api.mob.Mob
import worlds.gregs.hestia.game.api.movement.ChunkChanged
import worlds.gregs.hestia.game.events.MobChunkChanged

class MobChunkChangeSystem : ChunkChanged(Mob::class) {

    private lateinit var es: EventSystem

    override fun changedChunk(entityId: Int, from: Int, to: Int) {
        es.dispatch(MobChunkChanged(entityId, from, to))
    }
}
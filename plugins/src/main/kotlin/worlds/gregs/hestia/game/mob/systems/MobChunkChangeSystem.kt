package worlds.gregs.hestia.game.mob.systems

import worlds.gregs.hestia.game.mob.component.Mob
import worlds.gregs.hestia.game.entity.systems.EntityChunkChangeSystem

class MobChunkChangeSystem : EntityChunkChangeSystem(Mob::class) {

    private lateinit var map: MobChunkMap

    override fun changedChunk(entityId: Int, from: Int, to: Int) {
        map.remove(from, entityId)

        map.add(to, entityId)
    }
}
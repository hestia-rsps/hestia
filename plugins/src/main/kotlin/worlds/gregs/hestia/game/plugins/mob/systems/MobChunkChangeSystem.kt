package worlds.gregs.hestia.game.plugins.mob.systems

import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkChangeSystem
import worlds.gregs.hestia.game.plugins.mob.component.Mob

class MobChunkChangeSystem : EntityChunkChangeSystem(Mob::class) {

    private lateinit var map: MobChunkMap

    override fun changedChunk(entityId: Int, from: Int, to: Int) {
        map.remove(from, entityId)

        map.add(to, entityId)
    }
}
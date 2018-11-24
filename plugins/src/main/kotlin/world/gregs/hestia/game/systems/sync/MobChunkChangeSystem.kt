package world.gregs.hestia.game.systems.sync

import world.gregs.hestia.game.component.entity.Mob

class MobChunkChangeSystem : EntityChunkChangeSystem(Mob::class) {

    private lateinit var map: MobChunkMap

    override fun changedChunk(entityId: Int, from: Int, to: Int) {
        map.remove(from, entityId)

        map.add(to, entityId)
    }
}
package worlds.gregs.hestia.core.entity.mob.logic.systems.chunk.map

import worlds.gregs.hestia.core.entity.entity.api.EntityChunkMap
import worlds.gregs.hestia.core.entity.mob.api.MobChunk

class MobChunkMapSystem : MobChunk() {

    private lateinit var map: MobChunkMap

    override fun getMap(): EntityChunkMap {
        return map
    }
}
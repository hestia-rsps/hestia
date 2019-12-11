package worlds.gregs.hestia.core.plugins.mob.systems.chunk.map

import worlds.gregs.hestia.api.entity.EntityChunkMap
import worlds.gregs.hestia.api.mob.MobChunk

class MobChunkMapSystem : MobChunk() {

    private lateinit var map: MobChunkMap

    override fun getMap(): EntityChunkMap {
        return map
    }
}
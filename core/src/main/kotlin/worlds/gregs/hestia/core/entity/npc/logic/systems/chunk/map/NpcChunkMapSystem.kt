package worlds.gregs.hestia.core.entity.npc.logic.systems.chunk.map

import worlds.gregs.hestia.core.entity.entity.api.EntityChunkMap
import worlds.gregs.hestia.core.entity.npc.api.NpcChunk

class NpcChunkMapSystem : NpcChunk() {

    private lateinit var map: NpcChunkMap

    override fun getMap(): EntityChunkMap {
        return map
    }
}
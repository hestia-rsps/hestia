package worlds.gregs.hestia.core.entity.player.logic.systems.chunk.map

import worlds.gregs.hestia.core.entity.entity.api.EntityChunkMap
import worlds.gregs.hestia.core.entity.player.api.PlayerChunk

class PlayerChunkMapSystem : PlayerChunk() {

    private lateinit var map: PlayerChunkMap

    override fun getMap(): EntityChunkMap {
        return map
    }
}
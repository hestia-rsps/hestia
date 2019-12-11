package worlds.gregs.hestia.core.entity.player.logic.systems.chunk.map

import worlds.gregs.hestia.api.entity.EntityChunkMap
import worlds.gregs.hestia.api.player.PlayerChunk

class PlayerChunkMapSystem : PlayerChunk() {

    private lateinit var map: PlayerChunkMap

    override fun getMap(): EntityChunkMap {
        return map
    }
}
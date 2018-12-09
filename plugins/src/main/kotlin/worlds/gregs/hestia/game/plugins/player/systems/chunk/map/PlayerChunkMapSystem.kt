package worlds.gregs.hestia.game.plugins.player.systems.chunk.map

import worlds.gregs.hestia.game.api.player.Player
import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap
import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem

class PlayerChunkMapSystem : EntityChunkSystem(Player::class) {

    private lateinit var map: PlayerChunkMap

    override fun getMap(): EntityChunkMap {
        return map
    }
}
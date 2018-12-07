package worlds.gregs.hestia.game.plugins.player.systems.map

import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap
import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem
import worlds.gregs.hestia.game.plugins.player.component.Player

class PlayerChunkSystem : EntityChunkSystem(Player::class) {

    private lateinit var map: PlayerChunkMap

    override fun getMap(): EntityChunkMap {
        return map
    }
}
package worlds.gregs.hestia.game.player.systems.sync

import worlds.gregs.hestia.game.player.component.Player
import worlds.gregs.hestia.game.entity.systems.EntityChunkChangeSystem

class PlayerChunkChangeSystem : EntityChunkChangeSystem(Player::class) {

    private lateinit var map: PlayerChunkMap

    override fun changedChunk(entityId: Int, from: Int, to: Int) {
        map.remove(from, entityId)

        map.add(to, entityId)
    }
}
package worlds.gregs.hestia.game.plugins.player.systems.sync

import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkChangeSystem
import worlds.gregs.hestia.game.plugins.player.component.Player

class PlayerChunkChangeSystem : EntityChunkChangeSystem(Player::class) {

    private lateinit var map: PlayerChunkMap

    override fun changedChunk(entityId: Int, from: Int, to: Int) {
        map.remove(from, entityId)

        map.add(to, entityId)
    }
}
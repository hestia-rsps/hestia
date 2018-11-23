package world.gregs.hestia.game.systems.sync

import world.gregs.hestia.game.component.entity.Player

class PlayerChunkChangeSystem : EntityChunkChangeSystem(Player::class) {

    private lateinit var map: PlayerChunkMap

    override fun changedChunk(entityId: Int, from: Int, to: Int) {
        map.remove(from, entityId)

        map.add(to, entityId)
    }
}
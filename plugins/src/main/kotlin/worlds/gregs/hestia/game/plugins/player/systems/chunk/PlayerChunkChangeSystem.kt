package worlds.gregs.hestia.game.plugins.player.systems.chunk

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.movement.systems.ChunkChanged
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.game.events.PlayerChunkChanged

class PlayerChunkChangeSystem : ChunkChanged(Player::class) {

    private lateinit var es: EventSystem

    override fun changedChunk(entityId: Int, from: Int, to: Int) {
        es.dispatch(PlayerChunkChanged(entityId, from, to))
    }

}
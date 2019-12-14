package worlds.gregs.hestia.core.entity.player.logic.systems.chunk

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.entity.player.model.events.PlayerChunkChanged
import worlds.gregs.hestia.core.world.movement.api.systems.ChunkSubscription
import worlds.gregs.hestia.game.entity.Player

class PlayerChunkSubscriptionSystem : ChunkSubscription(Player::class) {

    private lateinit var es: EventSystem

    override fun changedChunk(entityId: Int, from: Int?, to: Int?) {
        es.dispatch(PlayerChunkChanged(entityId, from, to))
    }
}
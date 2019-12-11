package worlds.gregs.hestia.core.plugins.player.systems.chunk

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.movement.systems.ChunkSubscription
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.artemis.events.PlayerChunkChanged

class PlayerChunkSubscriptionSystem : ChunkSubscription(Player::class) {

    private lateinit var es: EventSystem

    override fun changedChunk(entityId: Int, from: Int?, to: Int?) {
        es.dispatch(PlayerChunkChanged(entityId, from, to))
    }
}
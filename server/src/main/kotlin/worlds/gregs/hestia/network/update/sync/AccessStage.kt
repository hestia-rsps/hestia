package worlds.gregs.hestia.network.update.sync

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.sync.SyncStage

/**
 * Encodes the start of finish of a bit access stage
 */
data class AccessStage(val start: Boolean) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        if(start) {
            builder.startBitAccess()
        } else {
            builder.finishBitAccess()
        }
    }

}
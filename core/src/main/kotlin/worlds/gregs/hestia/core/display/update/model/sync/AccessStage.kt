package worlds.gregs.hestia.core.display.update.model.sync

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.SyncStage

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

    companion object {
        val START = AccessStage(true)
        val END = AccessStage(false)
    }

}
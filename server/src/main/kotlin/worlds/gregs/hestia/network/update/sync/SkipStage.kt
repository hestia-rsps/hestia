package worlds.gregs.hestia.network.update.sync

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.sync.SyncStage

/**
 * Encodes a number of idle entities to skip
 */
data class SkipStage(val skip: Int) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 0)//No update needed
            when {
                skip == 0 -> {
                    writeBits(2, 0)
                }
                skip < 32 -> {
                    writeBits(2, 1)
                    writeBits(5, skip)
                }
                skip < 256 -> {
                    writeBits(2, 2)
                    writeBits(8, skip)
                }
                skip < 2048 -> {
                    writeBits(2, 3)
                    writeBits(11, skip)
                }
            }
        }
    }

}
package worlds.gregs.hestia.network.update.sync.mob

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.sync.SyncStage

data class MobSizeStage(val mobCount: Int) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(8, mobCount)
    }

}
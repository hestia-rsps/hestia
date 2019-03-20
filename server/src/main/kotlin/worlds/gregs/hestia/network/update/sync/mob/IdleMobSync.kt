package worlds.gregs.hestia.network.update.sync.mob

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.sync.SyncStage

class IdleMobSync : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(1, 0)
    }

}
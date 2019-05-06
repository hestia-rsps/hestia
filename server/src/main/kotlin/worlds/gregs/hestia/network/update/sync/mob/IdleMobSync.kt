package worlds.gregs.hestia.network.update.sync.mob

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.client.update.sync.SyncStage

object IdleMobSync : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(1, 0)
    }

}
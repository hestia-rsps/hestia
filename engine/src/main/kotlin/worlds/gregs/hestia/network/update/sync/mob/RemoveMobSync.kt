package worlds.gregs.hestia.network.update.sync.mob

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.client.update.sync.SyncStage
import worlds.gregs.hestia.api.client.update.sync.SyncStage.Companion.MOVE

object RemoveMobSync : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 1)//View Update
            writeBits(2, MOVE)//Movement type
        }
    }

}
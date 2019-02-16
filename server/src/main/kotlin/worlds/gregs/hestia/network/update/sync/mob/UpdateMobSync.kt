package worlds.gregs.hestia.network.update.sync.mob

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.sync.SyncStage
import worlds.gregs.hestia.game.update.sync.SyncStage.Companion.UPDATE

class UpdateMobSync : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(1, 1)//Update
        builder.writeBits(2, UPDATE)//Movement type
    }

}
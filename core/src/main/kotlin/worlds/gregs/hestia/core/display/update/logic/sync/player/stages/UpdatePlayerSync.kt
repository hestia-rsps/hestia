package worlds.gregs.hestia.core.display.update.logic.sync.player.stages

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.game.update.SyncStage.Companion.UPDATE

object UpdatePlayerSync : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 1)//View Update
            writeBits(1, 1)//Is block update required?
            writeBits(2, UPDATE)//Movement type
        }
        //Used for block update
    }

}
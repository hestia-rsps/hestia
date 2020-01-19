package worlds.gregs.hestia.core.display.update.logic.sync.npc.stages

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.game.update.SyncStage.Companion.UPDATE

object UpdateNpcSync : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(1, 1)//Update
        builder.writeBits(2, UPDATE)//Movement type
    }

}
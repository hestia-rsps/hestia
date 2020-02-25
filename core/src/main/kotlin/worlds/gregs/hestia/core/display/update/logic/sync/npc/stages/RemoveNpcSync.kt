package worlds.gregs.hestia.core.display.update.logic.sync.npc.stages

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.game.update.SyncStage.Companion.MOVE

object RemoveNpcSync : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 1)//View Update
            writeBits(2, MOVE)//Movement type
        }
    }

}
package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.sync.SyncStage
import worlds.gregs.hestia.game.update.sync.SyncStage.Companion.ADD

data class AddPlayerSync(val position: Position, val movement: SyncStage?) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, true)//View Update
            writeBits(2, ADD)//Movement type
            movement?.encode(this) ?: writeBits(1, 0)
            writeBits(6, position.xInRegion)
            writeBits(6, position.yInRegion)
            writeBits(1, 1)//Update
        }
    }

}
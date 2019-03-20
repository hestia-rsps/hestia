package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.sync.SyncStage
import worlds.gregs.hestia.game.update.sync.SyncStage.Companion.MOVE

data class MoveGlobalPlayerSync(val position: Position, val lastPosition: Position) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(1, true)//View Update
        builder.writeBits(2, MOVE)//Movement type
        //Calculate movement since last seen position
        Position.regionDelta(position, lastPosition) { deltaX, deltaY, deltaPlane ->
            builder.writeBits(18, (deltaY and 0xff) + (deltaX and 0xff shl 8) + (deltaPlane shl 16))
        }
    }

}
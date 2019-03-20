package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.sync.SyncStage
import worlds.gregs.hestia.game.update.sync.SyncStage.Companion.MOVE

data class MoveLocalPlayerSync(val position: Position, val mobile: Position, val global: Boolean, val update: Boolean) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 1)//View Update
            writeBits(1, update)//Is block update required?
            writeBits(2, MOVE)//Movement type
        }

        Position.delta(position, mobile) { deltaX, deltaY, deltaPlane ->
            builder.writeBits(1, global)
            if (global) {
                //Send global position
                builder.writeBits(30, (deltaY and 0x3fff) + (deltaX and 0x3fff shl 14) + (deltaPlane and 0x3 shl 28))
            } else {
                //Send local position
                val x = deltaX + if (deltaX < 0) 32 else 0
                val y = deltaY + if (deltaY < 0) 32 else 0
                builder.writeBits(12, y + (x shl 5) + (deltaPlane shl 10))
            }
        }
    }

}
package worlds.gregs.hestia.network.update.sync.mob

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.Direction
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getMobMoveDirection
import worlds.gregs.hestia.game.update.sync.SyncStage
import worlds.gregs.hestia.game.update.sync.SyncStage.Companion.RUNNING
import worlds.gregs.hestia.game.update.sync.SyncStage.Companion.WALKING

class MovementMobSync(private val nextWalkDirection: Direction, private val nextRunDirection: Direction?, private val update: Boolean) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 1)//View Update
            writeBits(2, if(nextRunDirection != null) RUNNING else WALKING)//Movement type
        }

        builder.apply {
            if (nextRunDirection != null) {
                writeBits(1, 1)
            }

            writeBits(3, getMobMoveDirection(nextWalkDirection))

            if (nextRunDirection != null) {
                writeBits(3, getMobMoveDirection(nextRunDirection))
            }

            writeBits(1, update)
        }
    }

}
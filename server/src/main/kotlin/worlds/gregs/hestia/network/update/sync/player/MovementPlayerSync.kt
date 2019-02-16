package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.update.Direction
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getPlayerRunningDirection
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getPlayerWalkingDirection
import worlds.gregs.hestia.game.update.sync.SyncStage

class MovementPlayerSync(private val nextWalkDirection: Direction, private val nextRunDirection: Direction?, private val update: Boolean) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 1)//View Update
            writeBits(1, update)//Is block update required?
        }

        //Calculate next step coordinates
        var dx = nextWalkDirection.deltaX
        var dy = nextWalkDirection.deltaY

        var running = false
        var direction = -1
        //If running
        if (nextRunDirection != null) {
            //Add additional movement
            dx += nextRunDirection.deltaX
            dy += nextRunDirection.deltaY

            //Calculate direction
            direction = getPlayerRunningDirection(dx, dy)
            //Running if valid
            if (direction != -1) {
                running = true
            }
        }

        //If not running calculate walking direction
        if (direction == -1) {
            direction = getPlayerWalkingDirection(dx, dy)
        }

        //If no movement, no direction
        if (dx == 0 && dy == 0) {
            direction = -1
        }

        if (direction == -1) {
            builder.writeBits(2, 0)//No movement
        } else {
            builder.writeBits(2, running.int + 1)
            builder.writeBits(running.int + 3, direction)//Direction
        }
    }

}
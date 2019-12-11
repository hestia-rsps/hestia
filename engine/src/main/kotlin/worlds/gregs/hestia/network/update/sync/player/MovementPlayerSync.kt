package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.client.update.block.Direction
import worlds.gregs.hestia.game.client.update.block.DirectionUtils.Companion.getPlayerRunningDirection
import worlds.gregs.hestia.game.client.update.block.DirectionUtils.Companion.getPlayerWalkingDirection
import worlds.gregs.hestia.api.client.update.sync.SyncStage

class MovementPlayerSync : SyncStage {
    lateinit var nextWalkDirection: Direction
    var nextRunDirection: Direction? = null
    var update: Boolean = false

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
            dx += nextRunDirection!!.deltaX
            dy += nextRunDirection!!.deltaY

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

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(MovementPlayerSync::class.java)

        fun create(nextWalkDirection: Direction, nextRunDirection: Direction?, update: Boolean): MovementPlayerSync {
            val obj = pool.obtain()
            obj.nextWalkDirection = nextWalkDirection
            obj.nextRunDirection = nextRunDirection
            obj.update = update
            return obj
        }
    }

}
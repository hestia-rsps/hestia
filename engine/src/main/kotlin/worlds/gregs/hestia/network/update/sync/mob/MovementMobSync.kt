package worlds.gregs.hestia.network.update.sync.mob

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.client.update.sync.SyncStage
import worlds.gregs.hestia.api.client.update.sync.SyncStage.Companion.RUNNING
import worlds.gregs.hestia.api.client.update.sync.SyncStage.Companion.WALKING
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.client.update.block.Direction
import worlds.gregs.hestia.game.client.update.block.DirectionUtils.Companion.getMobMoveDirection

class MovementMobSync : SyncStage {

    lateinit var nextWalkDirection: Direction
    var nextRunDirection: Direction? = null
    var update: Boolean = false

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
                writeBits(3, getMobMoveDirection(nextRunDirection!!))
            }

            writeBits(1, update)
        }
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(MovementMobSync::class.java)

        fun create(nextWalkDirection: Direction, nextRunDirection: Direction?, update: Boolean): MovementMobSync {
            val obj = pool.obtain()
            obj.nextWalkDirection = nextWalkDirection
            obj.nextRunDirection = nextRunDirection
            obj.update = update
            return obj
        }
    }

}
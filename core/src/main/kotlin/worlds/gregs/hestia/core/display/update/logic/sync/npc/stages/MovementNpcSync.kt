package worlds.gregs.hestia.core.display.update.logic.sync.npc.stages

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.core.display.update.logic.DirectionUtils.Companion.getNpcMoveDirection
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.game.update.SyncStage.Companion.RUNNING
import worlds.gregs.hestia.game.update.SyncStage.Companion.WALKING

class MovementNpcSync : SyncStage {

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

            writeBits(3, getNpcMoveDirection(nextWalkDirection))

            if (nextRunDirection != null) {
                writeBits(3, getNpcMoveDirection(nextRunDirection!!))
            }

            writeBits(1, update)
        }
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(MovementNpcSync::class.java)

        fun create(nextWalkDirection: Direction, nextRunDirection: Direction?, update: Boolean): MovementNpcSync {
            val obj = pool.obtain()
            obj.nextWalkDirection = nextWalkDirection
            obj.nextRunDirection = nextRunDirection
            obj.update = update
            return obj
        }
    }

}
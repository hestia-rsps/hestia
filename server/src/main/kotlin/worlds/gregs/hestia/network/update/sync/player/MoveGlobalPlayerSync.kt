package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.api.client.update.sync.SyncStage
import worlds.gregs.hestia.api.client.update.sync.SyncStage.Companion.MOVE
import worlds.gregs.hestia.game.entity.components.Position

class MoveGlobalPlayerSync : SyncStage {

    lateinit var position: Position
    lateinit var lastPosition: Position

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(1, true)//View Update
        builder.writeBits(2, MOVE)//Movement type
        //Calculate movement since last seen position
        Position.regionDelta(position, lastPosition) { deltaX, deltaY, deltaPlane ->
            builder.writeBits(18, (deltaY and 0xff) + (deltaX and 0xff shl 8) + (deltaPlane shl 16))
        }
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(MoveGlobalPlayerSync::class.java)

        fun create(position: Position, lastPosition: Position): MoveGlobalPlayerSync {
            val obj = pool.obtain()
            obj.position = position
            obj.lastPosition = lastPosition
            return obj
        }
    }

}
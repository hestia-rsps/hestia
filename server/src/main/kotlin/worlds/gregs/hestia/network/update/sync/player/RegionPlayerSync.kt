package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.client.update.block.Direction
import worlds.gregs.hestia.api.client.update.sync.SyncStage
import worlds.gregs.hestia.api.client.update.sync.SyncStage.Companion.REGION
import worlds.gregs.hestia.game.entity.components.Position

class RegionPlayerSync : SyncStage {

    lateinit var position: Position
    lateinit var lastPosition: Position

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(1, true)//View Update
        builder.writeBits(2, REGION)//Movement type
        //Calculate movement since last seen position
        Position.regionDelta(position, lastPosition) { deltaX, deltaY, deltaPlane ->
            val direction = Direction.getDirection(deltaX, deltaY)
            builder.writeBits(5, (deltaPlane shl 3) + (direction and 0x7))
        }
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(RegionPlayerSync::class.java)

        fun create(position: Position, lastPosition: Position): RegionPlayerSync {
            val obj = pool.obtain()
            obj.position = position
            obj.lastPosition = lastPosition
            return obj
        }
    }

}
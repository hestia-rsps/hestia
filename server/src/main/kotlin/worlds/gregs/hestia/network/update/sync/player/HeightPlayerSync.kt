package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.api.client.update.sync.SyncStage
import worlds.gregs.hestia.api.client.update.sync.SyncStage.Companion.HEIGHT
import worlds.gregs.hestia.game.entity.components.Position

class HeightPlayerSync : SyncStage {

    lateinit var position: Position
    lateinit var lastPosition: Position

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(1, true)//View Update
        builder.writeBits(2, HEIGHT)//Movement type
        //Calculate movement since last seen position
        Position.regionDelta(position, lastPosition) { _, _, deltaPlane ->
            builder.writeBits(2, deltaPlane)
        }
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(HeightPlayerSync::class.java)

        fun create(position: Position, lastPosition: Position): HeightPlayerSync {
            val obj = pool.obtain()
            obj.position = position
            obj.lastPosition = lastPosition
            return obj
        }
    }
}
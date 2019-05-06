package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.client.update.sync.SyncStage
import worlds.gregs.hestia.api.client.update.sync.SyncStage.Companion.ADD
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.entity.components.Position

class AddPlayerSync : SyncStage {

    lateinit var position: Position
    var movement: SyncStage? = null

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, true)//View Update
            writeBits(2, ADD)//Movement type
            movement?.encode(this) ?: writeBits(1, 0)
            writeBits(6, position.xInRegion)
            writeBits(6, position.yInRegion)
            writeBits(1, 1)//Update
        }
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(AddPlayerSync::class.java)

        fun create(position: Position, movement: SyncStage?): AddPlayerSync {
            val obj = pool.obtain()
            obj.position = position
            obj.movement = movement
            return obj
        }
    }
}
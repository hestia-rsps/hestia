package worlds.gregs.hestia.core.display.update.logic.sync.player.stages

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.game.update.SyncStage.Companion.MOVE
import worlds.gregs.hestia.core.entity.entity.model.components.Position

class MoveLocalPlayerSync : SyncStage {

    lateinit var position: Position
    lateinit var mobile: Position
    var global: Boolean = false
    var update: Boolean = false

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

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(MoveLocalPlayerSync::class.java)

        fun create(position: Position, mobile: Position, global: Boolean, update: Boolean): MoveLocalPlayerSync {
            val obj = pool.obtain()
            obj.position = position
            obj.mobile = mobile
            obj.global = global
            obj.update = update
            return obj
        }
    }

}
package worlds.gregs.hestia.core.display.update.logic.sync.mob.stages

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.core.display.update.logic.DirectionUtils
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.game.update.SyncStage

class AddMobSync : SyncStage {

    var clientIndex: Int = -1
    var facing: Facing? = null
    var update: Boolean = false
    lateinit var delta: Position
    var plane: Int = -1
    var mobType: Int = -1
    var regionChange: Boolean = false

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            //Client index
            writeBits(15, clientIndex)
            //Facing
            val direction = if (facing != null) DirectionUtils.getFaceDirection(facing!!.x, facing!!.y) else 0//A costly calculation for updating, so we do it here in the encoding thread
            writeBits(3, (direction shr 11) - 4)
            //Update
            writeBits(1, update)
            //Position y
            writeBits(5, delta.y + if (delta.y < 15) 32 else 0)
            //Position z
            writeBits(2, plane)
            //Mob id
            writeBits(15, mobType)//Mob id
            //Position x
            writeBits(5, delta.x + if (delta.x < 15) 32 else 0)
            //Moving region
            writeBits(1, regionChange)
        }
    }

    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(AddMobSync::class.java)

        fun create(clientIndex: Int, facing: Facing?, update: Boolean, delta: Position, plane: Int, mobType: Int, regionChange: Boolean): AddMobSync {
            val obj = pool.obtain()
            obj.apply {
                this.clientIndex = clientIndex
                this.facing = facing
                this.update = update
                this.delta = delta
                this.plane = plane
                this.mobType = mobType
                this.regionChange = regionChange
            }
            return obj
        }
    }

}
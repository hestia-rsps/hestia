package worlds.gregs.hestia.core.display.update.logic.sync.npc.stages

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.core.display.update.logic.DirectionUtils
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.game.update.SyncStage

class AddNpcSync : SyncStage {

    var clientIndex: Int = -1
    var facing: Facing? = null
    var update: Boolean = false
    lateinit var delta: Position
    var plane: Int = -1
    var npcType: Int = -1
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
            //Npc id
            writeBits(15, npcType)//Npc id
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
        private val pool = ConcurrentObjectPool(AddNpcSync::class.java)

        fun create(clientIndex: Int, facing: Facing?, update: Boolean, delta: Position, plane: Int, npcType: Int, regionChange: Boolean): AddNpcSync {
            val obj = pool.obtain()
            obj.apply {
                this.clientIndex = clientIndex
                this.facing = facing
                this.update = update
                this.delta = delta
                this.plane = plane
                this.npcType = npcType
                this.regionChange = regionChange
            }
            return obj
        }
    }

}
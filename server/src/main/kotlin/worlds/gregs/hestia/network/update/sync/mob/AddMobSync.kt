package worlds.gregs.hestia.network.update.sync.mob

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.sync.SyncStage

data class AddMobSync(val clientIndex: Int, val direction: Int, val update: Boolean, val delta: Position, val plane: Int, val mobType: Int, val regionChange: Boolean) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            //Client index
            writeBits(15, clientIndex)
            //Facing
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

}
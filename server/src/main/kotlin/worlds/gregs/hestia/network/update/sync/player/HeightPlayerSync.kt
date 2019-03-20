package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.sync.SyncStage
import worlds.gregs.hestia.game.update.sync.SyncStage.Companion.HEIGHT

data class HeightPlayerSync(val position: Position, val lastPosition: Position) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.writeBits(1, true)//View Update
        builder.writeBits(2, HEIGHT)//Movement type
        //Calculate movement since last seen position
        Position.regionDelta(position, lastPosition) { _, _, deltaPlane ->
            builder.writeBits(2, deltaPlane)
        }
    }

}
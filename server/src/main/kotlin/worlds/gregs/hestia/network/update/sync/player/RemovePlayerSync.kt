package worlds.gregs.hestia.network.update.sync.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.sync.SyncStage
import worlds.gregs.hestia.game.update.sync.SyncStage.Companion.REMOVE

data class RemovePlayerSync(val movement: SyncStage?) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        builder.apply {
            writeBits(1, 1)//View Update
            writeBits(1, 0)//Is block update required?
            writeBits(2, REMOVE)//Movement type
            movement?.encode(this) ?: writeBits(1, 0)
        }
    }

}
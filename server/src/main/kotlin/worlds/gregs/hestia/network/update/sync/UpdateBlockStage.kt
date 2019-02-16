package worlds.gregs.hestia.network.update.sync

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.block.UpdateBlock
import worlds.gregs.hestia.game.update.sync.SyncStage
import worlds.gregs.hestia.network.update.codec.MobEncoders
import worlds.gregs.hestia.network.update.codec.PlayerEncoders
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

/**
 * Encodes an entities [UpdateBlock]s
 */
data class UpdateBlockStage(val blocks: List<UpdateBlock>, val mob: Boolean) : SyncStage {

    override fun encode(builder: PacketBuilder) {
        var maskData = 0
        //Combine all the masks which need updating
        blocks.forEach {
            maskData = maskData or it.flag
        }

        if (maskData >= 256) {
            maskData = maskData or 0x80
        }
        if (maskData >= 65536) {
            maskData = maskData or if(mob) 0x8000 else 0x800
        }

        builder.writeByte(maskData)

        if (maskData >= 256) {
            builder.writeByte(maskData shr 8)
        }
        if (maskData >= 65536) {
            builder.writeByte(maskData shr 16)
        }

        //Write all of the update data
        blocks.forEach {
            encode(builder, it, mob)
        }
    }

    companion object {
        private val mobEncoders = MobEncoders()
        private val playerEncoders = PlayerEncoders()

        @Suppress("UNCHECKED_CAST")
        fun encode(builder: PacketBuilder, block: UpdateBlock, mob: Boolean) {
            if(mob) {
                (mobEncoders.get(block::class) as? UpdateBlockEncoder<UpdateBlock>)?.encode(builder, block) ?: throw IllegalStateException()
            } else {
                (playerEncoders.get(block::class) as? UpdateBlockEncoder<UpdateBlock>)?.encode(builder, block) ?: throw IllegalStateException()
            }
        }
    }
}
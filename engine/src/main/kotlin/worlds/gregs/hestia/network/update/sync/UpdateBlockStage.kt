package worlds.gregs.hestia.network.update.sync

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.game.update.UpdateBlock
import worlds.gregs.hestia.network.update.codec.MobEncoders
import worlds.gregs.hestia.network.update.codec.PlayerEncoders
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

/**
 * Encodes an entities [UpdateBlock]s
 */
class UpdateBlockStage : SyncStage {

    val blocks = ArrayList<UpdateBlock>()
    var mob: Boolean = false

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


    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(UpdateBlockStage::class.java)
        private val mobEncoders = MobEncoders()
        private val playerEncoders = PlayerEncoders()

        fun create(mob: Boolean): UpdateBlockStage {
            val obj = pool.obtain()
            obj.blocks.clear()
            obj.mob = mob
            return obj
        }

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
package worlds.gregs.hestia.network.update.sync

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.artemis.ConcurrentObjectPool
import worlds.gregs.hestia.game.update.SyncStage
import worlds.gregs.hestia.game.update.UpdateBlock
import worlds.gregs.hestia.network.update.codec.NpcEncoders
import worlds.gregs.hestia.network.update.codec.PlayerEncoders
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

/**
 * Encodes an entities [UpdateBlock]s
 */
class UpdateBlockStage : SyncStage {

    val blocks = ArrayList<UpdateBlock>()
    var npc: Boolean = false

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
            maskData = maskData or if(npc) 0x8000 else 0x800
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
            encode(builder, it, npc)
        }
    }


    override fun free() {
        pool.free(this)
    }

    companion object {
        private val pool = ConcurrentObjectPool(UpdateBlockStage::class.java)
        private val npcEncoders = NpcEncoders()
        private val playerEncoders = PlayerEncoders()

        fun create(npc: Boolean): UpdateBlockStage {
            val obj = pool.obtain()
            obj.blocks.clear()
            obj.npc = npc
            return obj
        }

        @Suppress("UNCHECKED_CAST")
        fun encode(builder: PacketBuilder, block: UpdateBlock, npc: Boolean) {
            if(npc) {
                (npcEncoders.get(block::class) as? UpdateBlockEncoder<UpdateBlock>)?.encode(builder, block) ?: throw IllegalStateException()
            } else {
                (playerEncoders.get(block::class) as? UpdateBlockEncoder<UpdateBlock>)?.encode(builder, block) ?: throw IllegalStateException()
            }
        }
    }
}
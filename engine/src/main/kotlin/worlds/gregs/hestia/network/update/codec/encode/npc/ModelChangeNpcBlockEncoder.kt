package worlds.gregs.hestia.network.update.codec.encode.npc

import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.npc.ModelChangeBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class ModelChangeNpcBlockEncoder : UpdateBlockEncoder<ModelChangeBlock> {

    override fun encode(builder: PacketBuilder, block: ModelChangeBlock) {
        val (_, models, colours, textures) = block
        var hash = 0
        //Reset
        if(models == null && colours == null && textures == null) {
            hash = 1
        } else {
            if(models != null) {
                hash = hash or 0x2
            }

            if(colours != null) {
                hash = hash or 0x4
            }

            if(textures != null) {
                hash = hash or 0x8
            }
        }


        builder.apply {
            writeByte(hash, Modifier.SUBTRACT)
            models?.forEach {
                writeShort(it)
            }
            colours?.forEach {
                writeShort(it)
            }
            textures?.forEach {
                writeShort(it, Modifier.ADD, Endian.LITTLE)
            }
        }
    }

}
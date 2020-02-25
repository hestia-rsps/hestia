package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.BatchAnimationBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class BatchAnimationBlockEncoder(private val npc: Boolean) : UpdateBlockEncoder<BatchAnimationBlock> {

    override fun encode(builder: PacketBuilder, block: BatchAnimationBlock) {
        builder.apply {
            writeByte(4)//Size
            for (i in 0 until 4) {
                if (npc) {
                    writeShort(if (i == 1) 867 else 866, order = Endian.LITTLE)//Animation id
                    writeByte(1, Modifier.ADD)//Some kind of count down/delay before checking for end of animation
                    writeShort(1)//Boolean
                } else {
                    writeShort(if (i == 1) 867 else 866)//Animation id
                    writeByte(1, Modifier.INVERSE)//Some kind of count down/delay before checking for end of animation
                    writeShort(1, Modifier.ADD, Endian.LITTLE)//Boolean
                }
            }
        }
    }

}
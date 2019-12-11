package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.client.update.block.blocks.GraphicBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class GraphicPlayerBlockEncoder : UpdateBlockEncoder<GraphicBlock> {

    override fun encode(builder: PacketBuilder, block: GraphicBlock) {
        val (_, type, id, trajectory, details) = block
        builder.apply {
            when (type) {
                1 -> {
                    writeShort(id)
                    writeInt(trajectory, order = Endian.LITTLE)
                    writeByte(details, Modifier.ADD)
                }
                else -> {
                    writeShort(id, Modifier.ADD, Endian.LITTLE)
                    writeInt(trajectory, Modifier.INVERSE, Endian.MIDDLE)
                    writeByte(details, Modifier.INVERSE)
                }
            }
        }
    }

}
package worlds.gregs.hestia.network.update.codec.encode.mob

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.block.blocks.GraphicBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class GraphicMobBlockEncoder : UpdateBlockEncoder<GraphicBlock> {

    override fun encode(builder: PacketBuilder, block: GraphicBlock) {
        val (_, type, id, trajectory, details) = block

        builder.apply {
            when (type) {
                1 -> {
                    writeShort(id, order = Endian.LITTLE)
                    writeInt(trajectory, Modifier.INVERSE, Endian.MIDDLE)
                    writeByte(details, Modifier.INVERSE)
                }
                2 -> {
                    writeShort(id, Modifier.ADD)
                    writeInt(trajectory)
                    writeByte(details)
                }
                3 -> {
                    writeShort(id)
                    writeInt(trajectory)
                    writeByte(details, Modifier.ADD)
                }
                4 -> {
                    writeShort(id)
                    writeInt(trajectory)
                    writeByte(details)
                }
            }
        }
    }

}
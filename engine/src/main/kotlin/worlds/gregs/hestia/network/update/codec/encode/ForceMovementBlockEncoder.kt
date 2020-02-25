package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.ForceMovementBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class ForceMovementBlockEncoder(private val npc: Boolean) : UpdateBlockEncoder<ForceMovementBlock> {

    override fun encode(builder: PacketBuilder, block: ForceMovementBlock) {
        val (_, first, second, direction) = block
        builder.apply {
            writeByte(first.deltaX, Modifier.SUBTRACT)
            if(npc) {
                writeByte(first.deltaY, Modifier.SUBTRACT)
                writeByte(second.deltaX, Modifier.INVERSE)
                writeByte(second.deltaY, Modifier.INVERSE)
                writeShort(first.delay)
            } else {
                writeByte(first.deltaY)
                writeByte(second.deltaX, Modifier.ADD)
                writeByte(second.deltaY, Modifier.ADD)
                writeShort(first.delay, order = Endian.LITTLE)
            }
            writeShort(second.delay, Modifier.ADD, Endian.LITTLE)
            if(npc) {
                writeByte(direction, Modifier.SUBTRACT)
            } else {
                writeByte(direction, Modifier.ADD)
            }
        }
    }

}
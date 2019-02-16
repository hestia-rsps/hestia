package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.block.blocks.ForceMovementBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class ForceMovementBlockEncoder(private val mob: Boolean) : UpdateBlockEncoder<ForceMovementBlock> {

    override fun encode(builder: PacketBuilder, block: ForceMovementBlock) {
        val (_, first, second, direction) = block
        builder.apply {
            writeByte(first.deltaX, Modifier.SUBTRACT)
            if(mob) {
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
            if(mob) {
                writeByte(direction, Modifier.SUBTRACT)
            } else {
                writeByte(direction, Modifier.ADD)
            }
        }
    }

}
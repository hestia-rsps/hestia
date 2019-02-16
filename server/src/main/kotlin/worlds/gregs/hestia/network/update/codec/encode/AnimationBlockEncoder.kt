package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.block.blocks.AnimationBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class AnimationBlockEncoder(private val mob: Boolean) : UpdateBlockEncoder<AnimationBlock> {

    override fun encode(builder: PacketBuilder, block: AnimationBlock) {
        val (_, first, second, third, fourth, speed) = block
        builder.apply {
            if(mob) {
                writeShort(first)
                writeShort(second)
                writeShort(third)
                writeShort(fourth)
                writeByte(speed)
            } else {
                writeShort(first, order = Endian.LITTLE)
                writeShort(second, order = Endian.LITTLE)
                writeShort(third, order = Endian.LITTLE)
                writeShort(fourth, order = Endian.LITTLE)
                writeByte(speed, Modifier.ADD)
            }
        }
    }

}
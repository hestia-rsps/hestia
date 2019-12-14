package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.ColourOverlayBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class ColourOverlayBlockEncoder(private val mob: Boolean) : UpdateBlockEncoder<ColourOverlayBlock> {

    override fun encode(builder: PacketBuilder, block: ColourOverlayBlock) {
        val (_, delay, duration, colour) = block
        builder.apply {
            if(mob) {
                writeByte(colour and 0xFF, Modifier.ADD)//Hue
                writeByte(colour shr 8 and 0xFF, Modifier.SUBTRACT)//Saturation
                writeByte(colour shr 16 and 0xFF)//Luminance
                writeByte(colour shr 24 and 0xFF, Modifier.SUBTRACT)//Multiplier
                writeShort(delay, order = Endian.LITTLE)//Start
            } else {
                writeByte(colour and 0xFF, Modifier.SUBTRACT)//Hue
                writeByte(colour shr 8 and 0xFF, Modifier.SUBTRACT)//Saturation
                writeByte(colour shr 16 and 0xFF, Modifier.INVERSE)//Luminance
                writeByte(colour shr 24 and 0xFF)//Multiplier
                writeShort(delay, Modifier.ADD, Endian.LITTLE)//Start
            }
            writeShort(duration)//Finish
        }
    }

}
package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.ColourOverlay
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobColourOverlayMask(private val colourOverlayMapper: ComponentMapper<ColourOverlay>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val colourOverlay = colourOverlayMapper.get(other)
        val colour = colourOverlay.colour
        writeByte(colour and 0xFF, Modifier.ADD)//Hue
        writeByte(colour shr 8 and 0xFF, Modifier.SUBTRACT)//Saturation
        writeByte(colour shr 16 and 0xFF)//Luminance
        writeByte(colour shr 24 and 0xFF, Modifier.SUBTRACT)//Multiplier
        writeShort(colourOverlay.delay, order = Endian.LITTLE)//Start
        writeShort(colourOverlay.duration)//Finish
    }
}
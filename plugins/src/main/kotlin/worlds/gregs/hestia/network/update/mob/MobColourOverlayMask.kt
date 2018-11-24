package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.plugins.entity.components.update.ColourOverlay

class MobColourOverlayMask(private val colourOverlayMapper: ComponentMapper<ColourOverlay>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val colourOverlay = colourOverlayMapper.get(other)
        val colour = colourOverlay.colour
        writeByteA(colour and 0xFF)//Hue
        writeByteS(colour shr 8 and 0xFF)//Saturation
        writeByte(colour shr 16 and 0xFF)//Luminance
        writeByteS(colour shr 24 and 0xFF)//Multiplier
        writeLEShort(colourOverlay.delay)//Start
        writeShort(colourOverlay.duration)//Finish
    }
}
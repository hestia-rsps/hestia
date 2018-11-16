package world.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.game.component.update.UpdateUnknown

class UnknownMask(private val unknownMapper: ComponentMapper<UpdateUnknown>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeByteS(1)
        val x = 255
        val y = 255
        val pos = (x shl 14) or (y and 0x3fff)
        val settings = (pos) and 0xfffffff
        writeShortA((settings and 0xc000) or (settings shr 16))
        writeShort(settings)
        writeShortA(1)
    }

}
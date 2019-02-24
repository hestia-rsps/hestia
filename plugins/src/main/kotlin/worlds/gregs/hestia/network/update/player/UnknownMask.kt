package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.player.component.update.UpdateUnknown
import worlds.gregs.hestia.game.update.UpdateEncoder

class UnknownMask(private val unknownMapper: ComponentMapper<UpdateUnknown>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeByte(1, Modifier.SUBTRACT)
        val x = 255
        val y = 255
        val pos = (x shl 14) or (y and 0x3fff)
        val settings = (pos) and 0xfffffff
        writeShort((settings and 0xc000) or (settings shr 16), Modifier.ADD)
        writeShort(settings)
        writeShort(1, Modifier.ADD)
    }

}
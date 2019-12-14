package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.player.UnknownBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class UnknownPlayerBlockEncoder : UpdateBlockEncoder<UnknownBlock> {

    override fun encode(builder: PacketBuilder, block: UnknownBlock) {
        builder.apply {
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

}
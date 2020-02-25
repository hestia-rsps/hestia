package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.player.AppearanceBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class AppearanceBlockEncoder : UpdateBlockEncoder<AppearanceBlock> {

    override fun encode(builder: PacketBuilder, block: AppearanceBlock) {
        val data = block.data
        builder.apply {
            writeByte(data.size, Modifier.SUBTRACT)
            writeBytes(data)
        }
    }

}
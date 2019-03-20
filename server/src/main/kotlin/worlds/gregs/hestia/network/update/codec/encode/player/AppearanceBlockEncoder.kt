package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.block.blocks.player.AppearanceBlock
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
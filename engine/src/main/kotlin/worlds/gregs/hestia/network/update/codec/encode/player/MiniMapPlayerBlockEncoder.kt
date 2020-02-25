package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.player.MiniMapPlayerBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class MiniMapPlayerBlockEncoder : UpdateBlockEncoder<MiniMapPlayerBlock> {

    override fun encode(builder: PacketBuilder, block: MiniMapPlayerBlock) {
        builder.writeByte(block.dot, Modifier.SUBTRACT)
    }

}
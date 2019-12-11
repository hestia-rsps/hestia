package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.client.update.block.blocks.ForceChatBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class ForceChatBlockEncoder : UpdateBlockEncoder<ForceChatBlock> {

    override fun encode(builder: PacketBuilder, block: ForceChatBlock) {
        builder.writeString(block.text)
    }

}
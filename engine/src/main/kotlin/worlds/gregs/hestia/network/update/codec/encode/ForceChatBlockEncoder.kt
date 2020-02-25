package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.ForceChatBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class ForceChatBlockEncoder : UpdateBlockEncoder<ForceChatBlock> {

    override fun encode(builder: PacketBuilder, block: ForceChatBlock) {
        builder.writeString(block.text)
    }

}
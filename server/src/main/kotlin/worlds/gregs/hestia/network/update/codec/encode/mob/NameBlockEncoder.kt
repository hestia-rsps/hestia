package worlds.gregs.hestia.network.update.codec.encode.mob

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.client.update.block.blocks.mob.NameBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class NameBlockEncoder : UpdateBlockEncoder<NameBlock> {

    override fun encode(builder: PacketBuilder, block: NameBlock) {
        builder.writeString(block.name)
    }

}
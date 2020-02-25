package worlds.gregs.hestia.network.update.codec.encode.npc

import world.gregs.hestia.io.Endian
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.npc.TransformBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class TransformBlockEncoder : UpdateBlockEncoder<TransformBlock> {

    override fun encode(builder: PacketBuilder, block: TransformBlock) {
        builder.writeShort(block.npcId, order = Endian.LITTLE)
    }

}
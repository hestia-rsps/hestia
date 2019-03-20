package worlds.gregs.hestia.network.update.codec.encode.mob

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.block.blocks.mob.TransformBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class TransformBlockEncoder : UpdateBlockEncoder<TransformBlock> {

    override fun encode(builder: PacketBuilder, block: TransformBlock) {
        builder.writeShort(block.mobId, order = Endian.LITTLE)
    }

}
package worlds.gregs.hestia.network.update.codec.encode.mob

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.client.update.block.blocks.mob.FaceDirectionMobBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class FaceDirectionMobBlockEncoder : UpdateBlockEncoder<FaceDirectionMobBlock> {

    override fun encode(builder: PacketBuilder, block: FaceDirectionMobBlock) {
        val (_, position, direction) = block
        builder.apply {
            writeShort((position.x + direction.x) * 2 + 1, order = Endian.LITTLE)
            writeShort((position.y + direction.y) * 2 + 1, order = Endian.LITTLE)
        }
    }

}
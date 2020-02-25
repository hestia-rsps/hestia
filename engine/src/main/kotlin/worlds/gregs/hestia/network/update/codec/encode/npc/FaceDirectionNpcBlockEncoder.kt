package worlds.gregs.hestia.network.update.codec.encode.npc

import world.gregs.hestia.io.Endian
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.npc.FaceDirectionNpcBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class FaceDirectionNpcBlockEncoder : UpdateBlockEncoder<FaceDirectionNpcBlock> {

    override fun encode(builder: PacketBuilder, block: FaceDirectionNpcBlock) {
        val (_, x, y, directionX, directionY) = block
        builder.apply {
            writeShort((x + directionX) * 2 + 1, order = Endian.LITTLE)
            writeShort((y + directionY) * 2 + 1, order = Endian.LITTLE)
        }
    }

}
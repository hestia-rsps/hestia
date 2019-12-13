package worlds.gregs.hestia.network.update.codec.encode.mob

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.mob.FaceDirectionMobBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class FaceDirectionMobBlockEncoder : UpdateBlockEncoder<FaceDirectionMobBlock> {

    override fun encode(builder: PacketBuilder, block: FaceDirectionMobBlock) {
        val (_, x, y, directionX, directionY) = block
        builder.apply {
            writeShort((x + directionX) * 2 + 1, order = Endian.LITTLE)
            writeShort((y + directionY) * 2 + 1, order = Endian.LITTLE)
        }
    }

}
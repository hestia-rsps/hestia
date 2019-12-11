package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.client.update.block.DirectionUtils
import worlds.gregs.hestia.game.client.update.block.blocks.player.FaceDirectionPlayerBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class FaceDirectionPlayerBlockEncoder : UpdateBlockEncoder<FaceDirectionPlayerBlock> {

    override fun encode(builder: PacketBuilder, block: FaceDirectionPlayerBlock) {
        val (_, face) = block
        //Calculate done here to safe precious updating time
        builder.writeShort(if (face != null) DirectionUtils.getFaceDirection(face.x, face.y) else 0)
    }

}
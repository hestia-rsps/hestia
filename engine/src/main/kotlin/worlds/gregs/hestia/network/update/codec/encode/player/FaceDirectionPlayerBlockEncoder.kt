package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.player.FaceDirectionPlayerBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class FaceDirectionPlayerBlockEncoder : UpdateBlockEncoder<FaceDirectionPlayerBlock> {

    override fun encode(builder: PacketBuilder, block: FaceDirectionPlayerBlock) {
        val (_, face) = block
        //Calculate done here to safe precious updating time
        builder.writeShort(face)
    }

}
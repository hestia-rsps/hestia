package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.block.blocks.player.FaceDirectionPlayerBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class FaceDirectionPlayerBlockEncoder : UpdateBlockEncoder<FaceDirectionPlayerBlock> {

    override fun encode(builder: PacketBuilder, block: FaceDirectionPlayerBlock) {
        builder.writeShort(block.direction)
    }

}
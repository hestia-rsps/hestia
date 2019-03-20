package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.block.blocks.player.MoveTypeBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class MoveTypeBlockEncoder : UpdateBlockEncoder<MoveTypeBlock> {

    override fun encode(builder: PacketBuilder, block: MoveTypeBlock) {
        val (_, moving, walking, running) = block
        builder.writeByte(
                when {
                    moving -> 127
                    walking -> 1
                    running -> 2
                    else -> 0
                }, Modifier.INVERSE
        )
    }

}
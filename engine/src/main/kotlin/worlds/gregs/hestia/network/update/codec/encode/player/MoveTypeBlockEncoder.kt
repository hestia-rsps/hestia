package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.player.MoveTypeBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class MoveTypeBlockEncoder : UpdateBlockEncoder<MoveTypeBlock> {

    override fun encode(builder: PacketBuilder, block: MoveTypeBlock) {
        builder.writeByte(block.movementType, Modifier.INVERSE)
    }

}
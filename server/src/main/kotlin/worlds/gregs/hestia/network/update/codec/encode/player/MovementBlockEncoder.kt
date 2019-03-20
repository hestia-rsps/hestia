package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.block.blocks.player.MovementBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class MovementBlockEncoder : UpdateBlockEncoder<MovementBlock> {

    override fun encode(builder: PacketBuilder, block: MovementBlock) {
        builder.writeByte(block.run, Modifier.SUBTRACT)
    }

}
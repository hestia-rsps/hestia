package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.WatchEntityBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class WatchEntityBlockEncoder(private val npc: Boolean) : UpdateBlockEncoder<WatchEntityBlock> {

    override fun encode(builder: PacketBuilder, block: WatchEntityBlock) {
        if(npc) {
            builder.writeShort(block.entityIndex, order = Endian.LITTLE)
        } else {
            builder.writeShort(block.entityIndex, Modifier.ADD)
        }
    }

}
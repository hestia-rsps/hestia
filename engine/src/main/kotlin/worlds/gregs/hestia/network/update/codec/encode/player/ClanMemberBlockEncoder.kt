package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.player.ClanMemberBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class ClanMemberBlockEncoder : UpdateBlockEncoder<ClanMemberBlock> {

    override fun encode(builder: PacketBuilder, block: ClanMemberBlock) {
        builder.writeByte(block.sameClanChat, Modifier.INVERSE)
    }

}
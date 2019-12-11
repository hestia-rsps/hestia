package worlds.gregs.hestia.network.update.codec.encode.player

import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.client.update.block.blocks.player.ClanMemberBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class ClanMemberBlockEncoder : UpdateBlockEncoder<ClanMemberBlock> {

    override fun encode(builder: PacketBuilder, block: ClanMemberBlock) {
        builder.writeByte(block.sameClanChat, Modifier.INVERSE)
    }

}
package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.player.component.update.UpdateClanMember
import worlds.gregs.hestia.game.update.UpdateEncoder

class ClanMemberMask(private val clanMemberMapper: ComponentMapper<UpdateClanMember>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val clanMember = clanMemberMapper.get(other)
        writeByte(clanMember.inSameClanChat, Modifier.INVERSE)
    }

}
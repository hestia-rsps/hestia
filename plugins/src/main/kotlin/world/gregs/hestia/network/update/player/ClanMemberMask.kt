package world.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import world.gregs.hestia.game.component.update.UpdateClanMember

class ClanMemberMask(private val clanMemberMapper: ComponentMapper<UpdateClanMember>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val clanMember = clanMemberMapper.get(other)
        writeByteC(clanMember.inSameClanChat.int)
    }

}
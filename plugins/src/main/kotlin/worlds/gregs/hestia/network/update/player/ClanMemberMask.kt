package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.plugins.player.component.update.UpdateClanMember

class ClanMemberMask(private val clanMemberMapper: ComponentMapper<UpdateClanMember>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val clanMember = clanMemberMapper.get(other)
        writeByteC(clanMember.inSameClanChat.int)
    }

}
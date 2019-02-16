package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.ForceChat
import worlds.gregs.hestia.game.update.UpdateEncoder

class ForceChatMask(private val forceChatMapper: ComponentMapper<ForceChat>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeString(forceChatMapper.get(other)?.message ?: "")
    }

}
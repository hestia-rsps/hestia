package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.DisplayName
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobNameMask(private val displayNameMapper: ComponentMapper<DisplayName>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeString(displayNameMapper.get(other)?.name ?: "")
    }
}
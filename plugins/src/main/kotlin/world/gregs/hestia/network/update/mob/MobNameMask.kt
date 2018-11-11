package world.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.update.DisplayName
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class MobNameMask(private val displayNameMapper: ComponentMapper<DisplayName>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeString(displayNameMapper.get(other)?.name ?: "")
    }
}
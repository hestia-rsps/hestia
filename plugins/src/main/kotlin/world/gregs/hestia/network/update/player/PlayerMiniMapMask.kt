package world.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import world.gregs.hestia.game.component.update.PlayerMiniMapDot

class PlayerMiniMapMask(private val miniMapDotMapper: ComponentMapper<PlayerMiniMapDot>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeByteS(miniMapDotMapper.get(other)?.p?.int ?: 0)
    }

}
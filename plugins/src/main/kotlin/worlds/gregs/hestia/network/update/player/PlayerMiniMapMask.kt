package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.player.component.update.PlayerMiniMapDot
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerMiniMapMask(private val miniMapDotMapper: ComponentMapper<PlayerMiniMapDot>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeByte(miniMapDotMapper.get(other)?.p ?: false, Modifier.INVERSE)
    }

}
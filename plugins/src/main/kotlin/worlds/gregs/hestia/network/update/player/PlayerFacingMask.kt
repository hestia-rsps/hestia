package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.DirectionUtils
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Facing
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class PlayerFacingMask(private val facingMapper: ComponentMapper<Facing>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val direction = facingMapper.get(other)
        writeShort(if(direction != null) DirectionUtils.getFaceDirection(direction.x, direction.y) else 0)
    }

}
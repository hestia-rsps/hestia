package world.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.game.update.DirectionUtils
import world.gregs.hestia.game.component.update.direction.Facing
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class PlayerFacingMask(private val facingMapper: ComponentMapper<Facing>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val direction = facingMapper.get(other)
        writeShort(if(direction != null) DirectionUtils.getFaceDirection(direction.x, direction.y) else 0)
    }

}
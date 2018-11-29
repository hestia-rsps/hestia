package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Face
import worlds.gregs.hestia.game.update.DirectionUtils
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerFacingMask(private val faceMapper: ComponentMapper<Face>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val direction = faceMapper.get(other)
        writeShort(if(direction != null) DirectionUtils.getFaceDirection(direction.x, direction.y) else 0)
    }

}
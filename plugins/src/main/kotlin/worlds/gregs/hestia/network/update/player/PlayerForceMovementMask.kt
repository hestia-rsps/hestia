package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.entity.components.update.ForceMovement
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class PlayerForceMovementMask(private val positionMapper: ComponentMapper<Position>, private val forceMovementMapper: ComponentMapper<ForceMovement>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val position = positionMapper.get(other)
        val movement = forceMovementMapper.get(other)

        writeByteS(movement.firstPosition!!.x - position.x)
        writeByte(movement.firstPosition!!.y - position.y)
        writeByteA(if (movement.secondPosition == null) 0 else movement.secondPosition!!.x - position.x)
        writeByteA(if (movement.secondPosition == null) 0 else movement.secondPosition!!.y - position.y)
        writeLEShort(movement.firstDelay * 30)
        writeLEShortA(if (movement.secondPosition == null) 0 else movement.secondDelay * 30)
        writeByteA(movement.direction)
    }

}
package world.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.update.ForceMovement
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class MobForceMovementMask(private val positionMapper: ComponentMapper<Position>, private val forceMovementMapper: ComponentMapper<ForceMovement>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val position = positionMapper.get(other)
        val movement = forceMovementMapper.get(other)

        writeByteS(movement.firstPosition!!.x - position.x)
        writeByteS(movement.firstPosition!!.y - position.y)
        writeByteC(if (movement.secondPosition == null) 0 else movement.secondPosition!!.x - position.x)
        writeByteC(if (movement.secondPosition == null) 0 else movement.secondPosition!!.y - position.y)
        writeShort(movement.firstDelay * 30)
        writeLEShortA(if (movement.secondPosition == null) 0 else movement.secondDelay * 30)
        writeByteS(movement.direction)
    }

}
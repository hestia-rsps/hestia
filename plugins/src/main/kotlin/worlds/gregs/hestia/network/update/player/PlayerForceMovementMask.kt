package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.game.plugins.entity.components.update.ForceMovement
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerForceMovementMask(private val positionMapper: ComponentMapper<Position>, private val forceMovementMapper: ComponentMapper<ForceMovement>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val position = positionMapper.get(other)
        val movement = forceMovementMapper.get(other)

        writeByte(movement.firstPosition!!.x - position.x, Modifier.SUBTRACT)
        writeByte(movement.firstPosition!!.y - position.y)
        writeByte(if (movement.secondPosition == null) 0 else movement.secondPosition!!.x - position.x, Modifier.ADD)
        writeByte(if (movement.secondPosition == null) 0 else movement.secondPosition!!.y - position.y, Modifier.ADD)
        writeShort(movement.firstDelay * 30, order = Endian.LITTLE)
        writeShort(if (movement.secondPosition == null) 0 else movement.secondDelay * 30, Modifier.ADD, Endian.LITTLE)
        writeByte(movement.direction, Modifier.ADD)
    }

}
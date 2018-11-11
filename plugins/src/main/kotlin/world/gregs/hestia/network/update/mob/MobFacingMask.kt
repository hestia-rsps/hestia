package world.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.update.direction.Facing
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class MobFacingMask(private val positionMapper: ComponentMapper<Position>, private val facingMapper: ComponentMapper<Facing>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val position = positionMapper.get(other)
        val direction = facingMapper.get(other)
        writeLEShort((position.x + direction.x) * 2 + 1)
        writeLEShort((position.y + direction.y) * 2 + 1)
    }

}
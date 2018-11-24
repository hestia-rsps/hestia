package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Facing
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class MobFacingMask(private val positionMapper: ComponentMapper<Position>, private val facingMapper: ComponentMapper<Facing>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val position = positionMapper.get(other)
        val direction = facingMapper.get(other)
        writeLEShort((position.x + direction.x) * 2 + 1)
        writeLEShort((position.y + direction.y) * 2 + 1)
    }

}
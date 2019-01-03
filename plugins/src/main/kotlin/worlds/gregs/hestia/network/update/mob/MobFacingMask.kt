package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Face
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobFacingMask(private val positionMapper: ComponentMapper<Position>, private val faceMapper: ComponentMapper<Face>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val position = positionMapper.get(other)
        val direction = faceMapper.get(other)
        writeLEShort((position.x + direction.x) * 2 + 1)
        writeLEShort((position.y + direction.y) * 2 + 1)
    }

}
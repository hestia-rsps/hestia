package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Face
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobFacingMask(private val positionMapper: ComponentMapper<Position>, private val faceMapper: ComponentMapper<Face>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val position = positionMapper.get(other)
        val direction = faceMapper.get(other)
        writeShort((position.x + direction.x) * 2 + 1, order = Endian.LITTLE)
        writeShort((position.y + direction.y) * 2 + 1, order = Endian.LITTLE)
    }

}
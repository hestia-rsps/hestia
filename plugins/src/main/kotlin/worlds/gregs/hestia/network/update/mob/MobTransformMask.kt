package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.Transform
import worlds.gregs.hestia.game.plugins.entity.components.update.Type
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobTransformMask(private val transformMapper: ComponentMapper<Transform>, typeMapper: ComponentMapper<Type>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        var mobId = transformMapper.get(other)?.mobId ?: -1
        if(mobId == -1) {
            mobId = typeMapper.get(other).id
        }
        writeShort(mobId, order = Endian.LITTLE)
    }
}
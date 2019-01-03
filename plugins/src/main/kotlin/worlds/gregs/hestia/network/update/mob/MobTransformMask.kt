package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.entity.components.update.Transform
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.plugins.entity.components.update.Type

class MobTransformMask(private val transformMapper: ComponentMapper<Transform>, typeMapper: ComponentMapper<Type>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        var mobId = transformMapper.get(other)?.mobId ?: -1
        if(mobId == -1) {
            mobId = typeMapper.get(other).id
        }
        writeLEShort(mobId)
    }
}
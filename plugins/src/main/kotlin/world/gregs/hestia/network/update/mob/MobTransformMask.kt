package world.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.update.Transform
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.game.component.entity.Type

class MobTransformMask(private val transformMapper: ComponentMapper<Transform>, typeMapper: ComponentMapper<Type>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        var mobId = transformMapper.get(other)?.mobId ?: -1
        if(mobId == -1) {
            mobId = typeMapper.get(other).id
        }
        writeLEShort(mobId)
    }
}
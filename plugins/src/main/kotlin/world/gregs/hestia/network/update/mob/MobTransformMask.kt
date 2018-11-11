package world.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.update.Transform
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class MobTransformMask(private val transformMapper: ComponentMapper<Transform>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeLEShort(transformMapper.get(other)?.mobId ?: -1)
    }
}
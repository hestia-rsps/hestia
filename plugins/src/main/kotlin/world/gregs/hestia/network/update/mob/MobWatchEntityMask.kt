package world.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.update.direction.Watching
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class MobWatchEntityMask(private val watchingMapper: ComponentMapper<Watching>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeLEShort(watchingMapper.get(other)?.clientIndex ?: -1)
    }

}
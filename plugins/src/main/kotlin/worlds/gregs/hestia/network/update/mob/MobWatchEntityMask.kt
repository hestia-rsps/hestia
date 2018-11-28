package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Watching
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class MobWatchEntityMask(private val watchingMapper: ComponentMapper<Watching>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeLEShort(watchingMapper.get(other)?.clientIndex ?: -1)
    }

}
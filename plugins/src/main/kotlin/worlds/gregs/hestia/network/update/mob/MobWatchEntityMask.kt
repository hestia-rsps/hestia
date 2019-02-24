package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Watching
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobWatchEntityMask(private val watchingMapper: ComponentMapper<Watching>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeShort(watchingMapper.get(other)?.clientIndex ?: -1, order = Endian.LITTLE)
    }

}
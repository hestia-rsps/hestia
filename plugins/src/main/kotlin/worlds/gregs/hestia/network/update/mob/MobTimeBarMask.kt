package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.plugins.entity.components.update.TimeBar

class MobTimeBarMask(private val timeBarMapper: ComponentMapper<TimeBar>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val timeBar = timeBarMapper.get(other)
        writeLEShortA((timeBar.full.int * 0x8000) or (timeBar.exponentialDelay and 0x7fff))
        writeByteC(timeBar.delay)
        writeByteC(timeBar.increment)
    }

}
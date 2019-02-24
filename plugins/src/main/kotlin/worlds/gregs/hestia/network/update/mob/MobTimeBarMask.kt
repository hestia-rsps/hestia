package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.plugins.entity.components.update.TimeBar
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobTimeBarMask(private val timeBarMapper: ComponentMapper<TimeBar>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val timeBar = timeBarMapper.get(other)
        writeShort((timeBar.full.int * 0x8000) or (timeBar.exponentialDelay and 0x7fff), Modifier.ADD, Endian.LITTLE)
        writeByte(timeBar.delay, Modifier.INVERSE)
        writeByte(timeBar.increment, Modifier.INVERSE)
    }

}
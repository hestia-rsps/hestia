package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.plugins.entity.components.update.TimeBar
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerTimeBarMask(private val timeBarMapper: ComponentMapper<TimeBar>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val timeBar = timeBarMapper.get(other)

        /*
        Time bar - white bar which sits under health bar
        You seem to be able to turn it red/green if the delay is a large number
        Used in dungeoneering?

        Displays for a base of 4 ticks
        startFull - whether the bar starts completely full
        exponentialDelay - how much more time the delay is active for (appears to be increments of 3 ticks)
        delay - Time after the bar is full until it disappears
        increment - how often the bar updates (higher = choppier, 0 = no increase)

        bar increment calculation is:
        if(increment != 0) (hashDelay - remainingCycles) / increment * increment
        */

        writeShort((timeBar.full.int * 0x8000) or (timeBar.exponentialDelay and 0x7fff), Modifier.ADD, Endian.LITTLE)
        writeByte(timeBar.delay)
        writeByte(timeBar.increment, Modifier.INVERSE)
    }

}
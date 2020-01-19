package worlds.gregs.hestia.network.update.codec.encode

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.TimeBarBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class TimeBarBlockEncoder(private val npc: Boolean) : UpdateBlockEncoder<TimeBarBlock> {

    override fun encode(builder: PacketBuilder, block: TimeBarBlock) {
        val (_, details, delay, increment) = block
        /*
        Time bar - white bar which sits under health bar
        You seem to be able to turn it red/green if the delay is a large number
        Used for glacors

        Displays for a base of 4 ticks
        startFull - whether the bar starts completely full
        exponentialDelay - how much more time the delay is active for (appears to be increments of 3 ticks)
        delay - Time after the bar is full until it disappears
        increment - how often the bar updates (higher = choppier, 0 = no increase)

        bar increment calculation is:
        if(increment != 0) (hashDelay - remainingCycles) / increment * increment
        */

        builder.apply {
            writeShort(details, Modifier.ADD, Endian.LITTLE)
            if(npc) {
                writeByte(delay, Modifier.INVERSE)
            } else {
                writeByte(delay)
            }
            writeByte(increment, Modifier.INVERSE)
        }
    }

}
package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.component.movement.Moving
import worlds.gregs.hestia.game.component.movement.RunToggled
import worlds.gregs.hestia.game.component.movement.Walking
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.component.movement.Running

class PlayerMoveTypeMask(private val walkingMapper: ComponentMapper<Walking>, private val runningMapper: ComponentMapper<Running>, private val movingMapper: ComponentMapper<Moving>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val moving = movingMapper.has(other)
        val walking = walkingMapper.has(other)
        val running = runningMapper.has(other)

        writeByteC(
                when {
                    moving -> 127
                    walking -> 1
                    running -> 2
                    else -> 0
                }
        )
    }

}
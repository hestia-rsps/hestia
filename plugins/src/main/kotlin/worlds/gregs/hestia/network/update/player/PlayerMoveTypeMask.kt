package worlds.gregs.hestia.network.update.player

import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.api.movement.Move
import worlds.gregs.hestia.game.api.movement.Run
import worlds.gregs.hestia.game.api.movement.Walk
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerMoveTypeMask(private val walk: Walk?, private val run: Run?, private val move: Move?) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val moving = move?.isMoving(other) ?: false
        val walking = walk?.isWalking(other) ?: false
        val running = run?.isRunning(other) ?: false

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
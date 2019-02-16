package worlds.gregs.hestia.network.update.player

import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.movement.types.Move
import worlds.gregs.hestia.api.movement.types.Run
import worlds.gregs.hestia.api.movement.types.Walk
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerMoveTypeMask(private val walk: Walk?, private val run: Run?, private val move: Move?) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val moving = move?.isMoving(other) ?: false
        val walking = walk?.isWalking(other) ?: false
        val running = run?.isRunning(other) ?: false

        writeByte(
                when {
                    moving -> 127
                    walking -> 1
                    running -> 2
                    else -> 0
                }, Modifier.INVERSE
        )
    }

}
package worlds.gregs.hestia.network.update.player

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.api.movement.types.Run
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerMovementMask(private val run: Run?) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeByteS(run?.isRunning(other).int + 1)
    }

}
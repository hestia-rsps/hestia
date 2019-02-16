package worlds.gregs.hestia.network.update.player

import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.api.movement.types.Run
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerMovementMask(private val run: Run?) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeByte(run?.isRunning(other).int + 1, Modifier.SUBTRACT)
    }

}
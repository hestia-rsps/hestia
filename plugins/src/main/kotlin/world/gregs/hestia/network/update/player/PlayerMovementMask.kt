package world.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.movement.Running
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int

class PlayerMovementMask(private val runningMapper: ComponentMapper<Running>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeByteS(runningMapper.has(other).int + 1)
    }

}
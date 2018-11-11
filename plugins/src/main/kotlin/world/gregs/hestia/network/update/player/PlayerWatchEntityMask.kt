package world.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.update.direction.Watching
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class PlayerWatchEntityMask(private val watchingMapper: ComponentMapper<Watching>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeShortA(watchingMapper.get(other)?.clientIndex ?: -1)
    }

}
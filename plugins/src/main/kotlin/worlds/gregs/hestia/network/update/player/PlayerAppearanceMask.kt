package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.player.component.update.AppearanceData
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class PlayerAppearanceMask(private val appearanceDataMapper: ComponentMapper<AppearanceData>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        val data = appearanceDataMapper.get(other)?.data
        writeByteS(data?.size ?: 0)
        writeBytes(data ?: byteArrayOf())
    }

}
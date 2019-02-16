package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.player.component.update.AppearanceData
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerAppearanceMask(private val appearanceDataMapper: ComponentMapper<AppearanceData>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val data = appearanceDataMapper.get(other)?.data
        writeByte(data?.size ?: 0, Modifier.SUBTRACT)
        writeBytes(data ?: byteArrayOf())
    }

}
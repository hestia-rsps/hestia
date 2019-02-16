package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.api.core.components.Graphics
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.FirstGraphic
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerGraphicMask(private val componentMapper: ComponentMapper<out Graphics>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        val graphic = componentMapper.get(other)

        //Speed/Height details
        val hash = if (graphic != null) {
            (graphic.delay and 0xffff) or (graphic.height shl 16)
        } else {
            0
        }

        //Rotation/refresh details
        val hash2 = if (graphic != null) {
            (graphic.rotation and 0x7) or (/*graphic.slot*/0 shl 3) or (graphic.forceRefresh.int shl 7)
        } else {
            0
        }

        when (graphic) {
            is FirstGraphic -> {
                writeShort(graphic.id)
                writeInt(hash, order = Endian.LITTLE)
                writeByte(hash2, Modifier.ADD)
            }
            else -> {
                writeShort(graphic?.id ?: -1, Modifier.ADD, Endian.LITTLE)
                writeInt(hash, Modifier.INVERSE, Endian.MIDDLE)
                writeByte(hash2, Modifier.INVERSE)
            }
        }
    }
}
package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.api.core.components.Graphics
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.FirstGraphic
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.FourthGraphic
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.SecondGraphic
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.ThirdGraphic
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobGraphicMask(private val componentMapper: ComponentMapper<out Graphics>) : UpdateEncoder {

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
                writeShort(graphic.id, order = Endian.LITTLE)
                writeInt(hash, Modifier.INVERSE, Endian.MIDDLE)
                writeByte(hash2, Modifier.INVERSE)
            }
            is SecondGraphic -> {
                writeShort(graphic.id, Modifier.ADD)
                writeInt(hash)
                writeByte(hash2)
            }
            is ThirdGraphic -> {
                writeShort(graphic.id)
                writeInt(hash)
                writeByte(hash2, Modifier.ADD)
            }
            is FourthGraphic -> {
                writeShort(graphic.id)
                writeInt(hash)
                writeByte(hash2)
            }
        }
    }
}
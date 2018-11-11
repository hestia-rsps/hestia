package world.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import world.gregs.hestia.game.component.update.gfx.*

class MobGraphicMask(private val componentMapper: ComponentMapper<out Graphics>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
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
                writeLEShort(graphic.id)
                writeInt2(hash)
                writeByteC(hash2)
            }
            is SecondGraphic -> {
                writeShortA(graphic.id)
                writeInt(hash)
                writeByte(hash2)
            }
            is ThirdGraphic -> {
                writeShort(graphic.id)
                writeInt(hash)
                writeByteA(hash2)
            }
            is FourthGraphic -> {
                writeShort(graphic.id)
                writeInt(hash)
                writeByte(hash2)
            }
        }
    }
}
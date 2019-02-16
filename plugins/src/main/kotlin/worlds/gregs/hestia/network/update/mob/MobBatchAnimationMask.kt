package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.BatchAnimations
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobBatchAnimationMask(private val batchAnimationsMapper: ComponentMapper<BatchAnimations>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, _ ->
        writeByte(4)//Size
        for(i in 0 until 4) {
            writeShort(if(i == 1) 867 else 866, order = Endian.LITTLE)//Animation id
            writeByte(1, Modifier.ADD)//Some kind of count down/delay before checking for end of animation
            writeShort(1)//Boolean
        }
    }

}
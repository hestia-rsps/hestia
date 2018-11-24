package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.plugins.entity.components.update.BatchAnimations

class MobBatchAnimationMask(private val batchAnimationsMapper: ComponentMapper<BatchAnimations>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeByte(4)//Size
        for(i in 0 until 4) {
            writeLEShort(if(i == 1) 867 else 866)//Animation id
            writeByteA(1)//Some kind of count down/delay before checking for end of animation
            writeShort(1)//Boolean
        }
    }

}
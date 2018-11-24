package world.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.game.component.update.BatchAnimations

class PlayerBatchAnimationMask(private val batchAnimationsMapper: ComponentMapper<BatchAnimations>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeByte(4)//Size
        for(i in 0 until 4) {
            writeShort(if(i == 1) 867 else 866)//Animation id
            writeByteC(1)//Some kind of count down/delay before checking for end of animation
            writeLEShortA(1)//Boolean
        }
    }

}
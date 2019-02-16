package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.BatchAnimations
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerBatchAnimationMask(private val batchAnimationsMapper: ComponentMapper<BatchAnimations>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeByte(4)//Size
        for(i in 0 until 4) {
            writeShort(if(i == 1) 867 else 866)//Animation id
            writeByte(1, Modifier.INVERSE)//Some kind of count down/delay before checking for end of animation
            writeShort(1,Modifier.ADD, Endian.LITTLE)//Boolean
        }
    }

}
package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.component.update.anim.FirstAnimation
import worlds.gregs.hestia.game.component.update.anim.FourthAnimation
import worlds.gregs.hestia.game.component.update.anim.SecondAnimation
import worlds.gregs.hestia.game.component.update.anim.ThirdAnimation
import worlds.gregs.hestia.game.update.UpdateEncoder
import world.gregs.hestia.core.network.packets.Packet

class PlayerAnimMask(private val firstAnimationMapper: ComponentMapper<FirstAnimation>, private val secondAnimationMapper: ComponentMapper<SecondAnimation>, private val thirdAnimationMapper: ComponentMapper<ThirdAnimation>, private val fourthAnimationMapper: ComponentMapper<FourthAnimation>) : UpdateEncoder {

    override val encode: Packet.Builder.(Int, Int) -> Unit = { _, other ->
        writeLEShort(get(firstAnimationMapper, other)?.id ?: -1)
        writeLEShort(get(secondAnimationMapper, other)?.id ?: -1)
        writeLEShort(get(thirdAnimationMapper, other)?.id ?: -1)
        writeLEShort(get(fourthAnimationMapper, other)?.id ?: -1)
        writeByteA(get(firstAnimationMapper, other)?.speed ?: 0)
    }
}
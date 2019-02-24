package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.FirstAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.FourthAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.SecondAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.ThirdAnimation
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobAnimMask(private val firstAnimationMapper: ComponentMapper<FirstAnimation>, private val secondAnimationMapper: ComponentMapper<SecondAnimation>, private val thirdAnimationMapper: ComponentMapper<ThirdAnimation>, private val fourthAnimationMapper: ComponentMapper<FourthAnimation>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeShort(get(firstAnimationMapper, other)?.id ?: -1)
        writeShort(get(secondAnimationMapper, other)?.id ?: -1)
        writeShort(get(thirdAnimationMapper, other)?.id ?: -1)
        writeShort(get(fourthAnimationMapper, other)?.id ?: -1)
        writeByte(get(firstAnimationMapper, other)?.speed ?: 0)
    }
}
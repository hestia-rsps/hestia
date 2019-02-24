package worlds.gregs.hestia.network.update.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.FirstAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.FourthAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.SecondAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.ThirdAnimation
import worlds.gregs.hestia.game.update.UpdateEncoder

class PlayerAnimMask(private val firstAnimationMapper: ComponentMapper<FirstAnimation>, private val secondAnimationMapper: ComponentMapper<SecondAnimation>, private val thirdAnimationMapper: ComponentMapper<ThirdAnimation>, private val fourthAnimationMapper: ComponentMapper<FourthAnimation>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeShort(get(firstAnimationMapper, other)?.id ?: -1, order = Endian.LITTLE)
        writeShort(get(secondAnimationMapper, other)?.id ?: -1, order = Endian.LITTLE)
        writeShort(get(thirdAnimationMapper, other)?.id ?: -1, order = Endian.LITTLE)
        writeShort(get(fourthAnimationMapper, other)?.id ?: -1, order = Endian.LITTLE)
        writeByte(get(firstAnimationMapper, other)?.speed ?: 0, Modifier.ADD)
    }
}
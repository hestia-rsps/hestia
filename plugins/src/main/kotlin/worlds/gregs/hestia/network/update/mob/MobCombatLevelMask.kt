package worlds.gregs.hestia.network.update.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.plugins.entity.components.update.CombatLevel
import worlds.gregs.hestia.game.update.UpdateEncoder

class MobCombatLevelMask(private val combatLevelMapper: ComponentMapper<CombatLevel>) : UpdateEncoder {

    override val encode: PacketBuilder.(Int, Int) -> Unit = { _, other ->
        writeShort(combatLevelMapper.get(other)?.level ?: 0, order = Endian.LITTLE)
    }

}
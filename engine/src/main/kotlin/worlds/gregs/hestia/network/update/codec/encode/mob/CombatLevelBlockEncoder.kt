package worlds.gregs.hestia.network.update.codec.encode.mob

import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.mob.CombatLevelBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class CombatLevelBlockEncoder : UpdateBlockEncoder<CombatLevelBlock> {

    override fun encode(builder: PacketBuilder, block: CombatLevelBlock) {
        builder.writeShort(block.combatLevel, order = Endian.LITTLE)
    }

}
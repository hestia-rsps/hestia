package worlds.gregs.hestia.network.update.codec.encode.npc

import world.gregs.hestia.io.Endian
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.blocks.npc.CombatLevelBlock
import worlds.gregs.hestia.network.update.codec.UpdateBlockEncoder

class CombatLevelBlockEncoder : UpdateBlockEncoder<CombatLevelBlock> {

    override fun encode(builder: PacketBuilder, block: CombatLevelBlock) {
        builder.writeShort(block.combatLevel, order = Endian.LITTLE)
    }

}
package worlds.gregs.hestia.game.update.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.CombatLevel
import worlds.gregs.hestia.game.update.components.UpdateCombatLevel
import worlds.gregs.hestia.game.update.block.blocks.mob.CombatLevelBlock
import worlds.gregs.hestia.services.Aspect

class CombatLevelMobBlockFactory(flag: Int) : BlockFactory<CombatLevelBlock>(Aspect.all(Renderable::class, UpdateCombatLevel::class), true, flag = flag, mob = true) {

    private lateinit var combatLevelMapper: ComponentMapper<CombatLevel>

    override fun create(player: Int, other: Int): CombatLevelBlock {
        return CombatLevelBlock(flag, combatLevelMapper.get(other)?.level
                ?: 65535)
    }

}
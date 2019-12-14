package worlds.gregs.hestia.core.display.update.logic.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.CombatLevel
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.UpdateCombatLevel
import worlds.gregs.hestia.game.update.blocks.mob.CombatLevelBlock
import worlds.gregs.hestia.artemis.Aspect

class CombatLevelMobBlockFactory(flag: Int) : BlockFactory<CombatLevelBlock>(Aspect.all(Renderable::class, UpdateCombatLevel::class), true, flag = flag, mob = true) {

    private lateinit var combatLevelMapper: ComponentMapper<CombatLevel>

    override fun create(player: Int, other: Int): CombatLevelBlock {
        return CombatLevelBlock(flag, combatLevelMapper.get(other)?.level
                ?: 65535)
    }

}
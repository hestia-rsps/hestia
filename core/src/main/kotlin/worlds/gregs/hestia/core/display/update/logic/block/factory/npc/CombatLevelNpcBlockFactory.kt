package worlds.gregs.hestia.core.display.update.logic.block.factory.npc

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.CombatLevel
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.UpdateCombatLevel
import worlds.gregs.hestia.game.update.blocks.npc.CombatLevelBlock

class CombatLevelNpcBlockFactory(flag: Int) : BlockFactory<CombatLevelBlock>(Aspect.all(Renderable::class, UpdateCombatLevel::class), true, flag = flag, npc = true) {

    private lateinit var combatLevelMapper: ComponentMapper<CombatLevel>

    override fun create(player: Int, other: Int): CombatLevelBlock {
        return CombatLevelBlock(flag, combatLevelMapper.get(other)?.level
                ?: 65535)
    }

}
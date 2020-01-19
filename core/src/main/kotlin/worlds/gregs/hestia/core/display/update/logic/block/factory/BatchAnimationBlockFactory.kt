package worlds.gregs.hestia.core.display.update.logic.block.factory

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.BatchAnimations
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.game.update.blocks.BatchAnimationBlock

open class BatchAnimationBlockFactory(flag: Int, npc: Boolean = false) : BlockFactory<BatchAnimationBlock>(Aspect.all(Renderable::class, BatchAnimations::class), flag = flag, npc = npc) {

    override fun create(player: Int, other: Int): BatchAnimationBlock {
        return BatchAnimationBlock(flag)
    }

}
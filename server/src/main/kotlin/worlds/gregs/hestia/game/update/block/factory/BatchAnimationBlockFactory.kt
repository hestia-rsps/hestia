package worlds.gregs.hestia.game.update.block.factory

import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.BatchAnimations
import worlds.gregs.hestia.game.update.block.blocks.BatchAnimationBlock
import worlds.gregs.hestia.services.Aspect

open class BatchAnimationBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<BatchAnimationBlock>(Aspect.all(Renderable::class, BatchAnimations::class), flag = flag, mob = mob) {

    override fun create(player: Int, other: Int): BatchAnimationBlock {
        return BatchAnimationBlock(flag)
    }

}
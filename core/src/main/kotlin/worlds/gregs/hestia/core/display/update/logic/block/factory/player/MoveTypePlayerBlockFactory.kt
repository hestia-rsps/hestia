package worlds.gregs.hestia.core.display.update.logic.block.factory.player

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.UpdateMoveType
import worlds.gregs.hestia.core.world.movement.api.types.Move
import worlds.gregs.hestia.core.world.movement.api.types.Run
import worlds.gregs.hestia.core.world.movement.api.types.Walk
import worlds.gregs.hestia.game.update.blocks.player.MoveTypeBlock

class MoveTypePlayerBlockFactory(flag: Int) : BlockFactory<MoveTypeBlock>(Aspect.all(Renderable::class, UpdateMoveType::class), true, flag = flag) {

    private val walk: Walk? = null
    private val run: Run? = null
    private val move: Move? = null

    override fun create(player: Int, other: Int): MoveTypeBlock {
        return MoveTypeBlock(flag, move?.isMoving(other)
                ?: false, walk?.isWalking(other) ?: false, run?.isRunning(other) ?: false)
    }

}
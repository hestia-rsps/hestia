package worlds.gregs.hestia.game.update.block.factory.player

import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.api.movement.types.Move
import worlds.gregs.hestia.api.movement.types.Run
import worlds.gregs.hestia.api.movement.types.Walk
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.UpdateMoveType
import worlds.gregs.hestia.game.update.block.blocks.player.MoveTypeBlock
import worlds.gregs.hestia.services.Aspect

class MoveTypePlayerBlockFactory(flag: Int) : BlockFactory<MoveTypeBlock>(Aspect.all(Renderable::class, UpdateMoveType::class), true, flag = flag) {

    private val walk: Walk? = null
    private val run: Run? = null
    private val move: Move? = null

    override fun create(player: Int, other: Int): MoveTypeBlock {
        return MoveTypeBlock(flag, move?.isMoving(other)
                ?: false, walk?.isWalking(other) ?: false, run?.isRunning(other) ?: false)
    }

}
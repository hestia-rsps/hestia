package worlds.gregs.hestia.game.update.block.factory.player

import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.api.movement.types.Run
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.UpdateMovement
import worlds.gregs.hestia.game.update.block.blocks.player.MovementBlock
import worlds.gregs.hestia.services.Aspect

class MovementPlayerBlockFactory(flag: Int) : BlockFactory<MovementBlock>(Aspect.all(Renderable::class, UpdateMovement::class), true, flag = flag) {

    private val run: Run? = null

    override fun create(player: Int, other: Int): MovementBlock {
        return MovementBlock(flag, run?.isRunning(other).int + 1)
    }

}
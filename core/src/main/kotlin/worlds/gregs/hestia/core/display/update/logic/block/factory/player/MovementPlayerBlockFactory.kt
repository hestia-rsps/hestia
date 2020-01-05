package worlds.gregs.hestia.core.display.update.logic.block.factory.player

import com.artemis.ComponentMapper
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.UpdateMovement
import worlds.gregs.hestia.core.world.movement.model.MovementType
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement
import worlds.gregs.hestia.game.update.blocks.player.MovementBlock

class MovementPlayerBlockFactory(flag: Int) : BlockFactory<MovementBlock>(Aspect.all(Renderable::class, UpdateMovement::class), true, flag = flag) {

    private lateinit var movementMapper: ComponentMapper<Movement>

    override fun create(player: Int, other: Int): MovementBlock {
        return MovementBlock(flag,  (movementMapper.get(other)?.actual == MovementType.Run).int + 1)
    }

}
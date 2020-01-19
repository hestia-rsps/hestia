package worlds.gregs.hestia.core.display.update.logic.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.UpdateMoveType
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement
import worlds.gregs.hestia.game.update.blocks.player.MoveTypeBlock

class MoveTypePlayerBlockFactory(flag: Int) : BlockFactory<MoveTypeBlock>(Aspect.all(Renderable::class, UpdateMoveType::class), true, flag = flag) {

    private lateinit var movementMapper: ComponentMapper<Movement>

    override fun create(player: Int, other: Int): MoveTypeBlock {
        return MoveTypeBlock(flag, movementMapper.get(other).visual.value)
    }

}
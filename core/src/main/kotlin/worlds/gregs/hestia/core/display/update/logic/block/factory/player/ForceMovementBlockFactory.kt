package worlds.gregs.hestia.core.display.update.logic.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.ForceMovement
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.game.update.blocks.DelayedTile
import worlds.gregs.hestia.game.update.blocks.ForceMovementBlock
import worlds.gregs.hestia.service.Aspect

open class ForceMovementBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<ForceMovementBlock>(Aspect.all(Renderable::class, Position::class, ForceMovement::class), flag = flag, mob = mob) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var forceMovementMapper: ComponentMapper<ForceMovement>

    override fun create(player: Int, other: Int): ForceMovementBlock {
        val position = positionMapper.get(other)
        val movement = forceMovementMapper.get(other)
        val first = create(position, movement.firstPosition, movement.firstDelay)
        val second = create(position, movement.secondPosition, movement.secondDelay)
        return ForceMovementBlock(flag, first, second, movement.direction)
    }

    companion object {
        private val EMPTY = DelayedTile(0, 0, 0)

        private fun create(current: Position, target: Position?, delay: Int): DelayedTile {
            return if(target == null) EMPTY else DelayedTile(target.x - current.x, target.y - current.y, delay * 30)
        }
    }

}
package worlds.gregs.hestia.game.client.update.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.game.client.update.block.blocks.DelayedTile
import worlds.gregs.hestia.game.client.update.block.blocks.ForceMovementBlock
import worlds.gregs.hestia.api.client.update.components.ForceMovement
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.services.Aspect

open class ForceMovementBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<ForceMovementBlock>(Aspect.all(Renderable::class, Position::class, ForceMovement::class), flag = flag, mob = mob) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var forceMovementMapper: ComponentMapper<ForceMovement>

    override fun create(player: Int, other: Int): ForceMovementBlock {
        val position = positionMapper.get(other)
        val movement = forceMovementMapper.get(other)
        val first = DelayedTile.create(position, movement.firstPosition, movement.firstDelay)
        val second = DelayedTile.create(position, movement.secondPosition, movement.secondDelay)
        return ForceMovementBlock(flag, first, second, movement.direction)
    }

}
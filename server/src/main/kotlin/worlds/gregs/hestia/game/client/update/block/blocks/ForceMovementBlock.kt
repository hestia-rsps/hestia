package worlds.gregs.hestia.game.client.update.block.blocks

import worlds.gregs.hestia.api.client.update.block.UpdateBlock
import worlds.gregs.hestia.game.entity.components.Position

data class ForceMovementBlock(override val flag: Int, val firstTile: DelayedTile, val secondTile: DelayedTile, val direction: Int) : UpdateBlock

data class DelayedTile(val deltaX: Int, val deltaY: Int, val delay: Int) {

    companion object {
        private val EMPTY = DelayedTile(0, 0, 0)

        fun create(current: Position, target: Position?, delay: Int): DelayedTile {
            return if(target == null) EMPTY else DelayedTile(target.x - current.x, target.y - current.y, delay * 30)
        }

    }
}
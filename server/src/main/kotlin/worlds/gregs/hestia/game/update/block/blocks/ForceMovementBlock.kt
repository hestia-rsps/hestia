package worlds.gregs.hestia.game.update.block.blocks

import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.block.UpdateBlock

data class ForceMovementBlock(override val flag: Int, val firstTile: DelayedTile, val secondTile: DelayedTile, val direction: Int) : UpdateBlock

data class DelayedTile(val deltaX: Int, val deltaY: Int, val delay: Int) {

    companion object {
        private val EMPTY = DelayedTile(0, 0, 0)

        fun create(current: Position, target: Position?, delay: Int): DelayedTile {
            return if(target == null) EMPTY else DelayedTile(target.x - current.x, target.y - current.y, delay * 30)
        }

    }
}
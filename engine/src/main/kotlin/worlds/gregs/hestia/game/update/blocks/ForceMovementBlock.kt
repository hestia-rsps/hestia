package worlds.gregs.hestia.game.update.blocks

import worlds.gregs.hestia.game.update.UpdateBlock

data class ForceMovementBlock(override val flag: Int, val firstTile: DelayedTile, val secondTile: DelayedTile, val direction: Int) : UpdateBlock

data class DelayedTile(val deltaX: Int, val deltaY: Int, val delay: Int)
package worlds.gregs.hestia.game.update.blocks.player

import worlds.gregs.hestia.game.update.UpdateBlock

/**
 * @param movementType walk=1, run=2, instant move=127
 */
data class MoveTypeBlock(override val flag: Int, val movementType: Int) : UpdateBlock
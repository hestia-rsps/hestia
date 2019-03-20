package worlds.gregs.hestia.game.update.block.blocks

import worlds.gregs.hestia.game.update.block.UpdateBlock

/**
 * @param delay whether the bar is full & exponentialDelay
 */
data class TimeBarBlock(override val flag: Int, val details: Int, var delay: Int, var increment: Int) : UpdateBlock
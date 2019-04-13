package worlds.gregs.hestia.game.client.update.block.blocks

import worlds.gregs.hestia.api.client.update.block.UpdateBlock

/**
 * @param id Graphic id
 * @param trajectory speed & height
 * @param details rotation, slot & refresh
 */
data class GraphicBlock(override val flag: Int, val type: Int, val id: Int, val trajectory: Int, val details: Int) : UpdateBlock
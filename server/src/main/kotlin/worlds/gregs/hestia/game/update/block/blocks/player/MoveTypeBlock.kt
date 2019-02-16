package worlds.gregs.hestia.game.update.block.blocks.player

import worlds.gregs.hestia.game.update.block.UpdateBlock

data class MoveTypeBlock(override val flag: Int, val moving: Boolean, val walking: Boolean, val running: Boolean) : UpdateBlock
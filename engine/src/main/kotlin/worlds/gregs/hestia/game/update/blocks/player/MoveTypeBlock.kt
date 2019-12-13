package worlds.gregs.hestia.game.update.blocks.player

import worlds.gregs.hestia.game.update.UpdateBlock

data class MoveTypeBlock(override val flag: Int, val moving: Boolean, val walking: Boolean, val running: Boolean) : UpdateBlock
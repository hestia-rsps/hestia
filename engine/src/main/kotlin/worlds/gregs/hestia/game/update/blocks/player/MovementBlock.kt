package worlds.gregs.hestia.game.update.blocks.player

import worlds.gregs.hestia.game.update.UpdateBlock

data class MovementBlock(override val flag: Int, val run: Int) : UpdateBlock
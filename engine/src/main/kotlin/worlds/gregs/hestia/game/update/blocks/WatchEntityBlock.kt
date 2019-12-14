package worlds.gregs.hestia.game.update.blocks

import worlds.gregs.hestia.game.update.UpdateBlock

data class WatchEntityBlock(override val flag: Int, val entityIndex: Int) : UpdateBlock
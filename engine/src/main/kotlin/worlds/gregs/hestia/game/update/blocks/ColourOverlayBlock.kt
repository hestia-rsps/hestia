package worlds.gregs.hestia.game.update.blocks

import worlds.gregs.hestia.game.update.UpdateBlock

data class ColourOverlayBlock(override val flag: Int, var delay: Int, var duration: Int, var colour: Int) : UpdateBlock
package worlds.gregs.hestia.game.client.update.block.blocks

import worlds.gregs.hestia.api.client.update.block.UpdateBlock

data class ColourOverlayBlock(override val flag: Int, var delay: Int, var duration: Int, var colour: Int) : UpdateBlock
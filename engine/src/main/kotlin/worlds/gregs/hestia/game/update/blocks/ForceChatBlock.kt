package worlds.gregs.hestia.game.update.blocks

import worlds.gregs.hestia.game.update.UpdateBlock

data class ForceChatBlock(override val flag: Int, val text: String) : UpdateBlock
package worlds.gregs.hestia.game.update.blocks

import worlds.gregs.hestia.game.update.UpdateBlock

data class AnimationBlock(override val flag: Int, val first: Int, val second: Int, val third: Int, val fourth: Int, val speed: Int) : UpdateBlock
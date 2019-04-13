package worlds.gregs.hestia.game.client.update.block.blocks

import worlds.gregs.hestia.api.client.update.block.UpdateBlock

data class AnimationBlock(override val flag: Int, val first: Int, val second: Int, val third: Int, val fourth: Int, val speed: Int) : UpdateBlock
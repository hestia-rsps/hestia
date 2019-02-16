package worlds.gregs.hestia.game.update.block.blocks

import worlds.gregs.hestia.game.update.block.UpdateBlock
import worlds.gregs.hestia.game.update.components.Damage

data class HitsBlock(override val flag: Int, val damage: Damage, val player: Int, val other: Int) : UpdateBlock
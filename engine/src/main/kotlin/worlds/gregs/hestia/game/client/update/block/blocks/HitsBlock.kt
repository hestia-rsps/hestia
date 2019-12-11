package worlds.gregs.hestia.game.client.update.block.blocks

import worlds.gregs.hestia.api.client.update.block.UpdateBlock
import worlds.gregs.hestia.api.client.update.components.Damage

data class HitsBlock(override val flag: Int, val damage: Damage, val player: Int, val other: Int) : UpdateBlock
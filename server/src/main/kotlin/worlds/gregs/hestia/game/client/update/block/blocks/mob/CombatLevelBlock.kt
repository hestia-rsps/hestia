package worlds.gregs.hestia.game.client.update.block.blocks.mob

import worlds.gregs.hestia.api.client.update.block.UpdateBlock

data class CombatLevelBlock(override val flag: Int, val combatLevel: Int) : UpdateBlock
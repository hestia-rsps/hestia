package worlds.gregs.hestia.game.update.blocks.npc

import worlds.gregs.hestia.game.update.UpdateBlock

data class CombatLevelBlock(override val flag: Int, val combatLevel: Int) : UpdateBlock
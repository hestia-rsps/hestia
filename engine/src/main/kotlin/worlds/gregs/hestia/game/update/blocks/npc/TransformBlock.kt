package worlds.gregs.hestia.game.update.blocks.npc

import worlds.gregs.hestia.game.update.UpdateBlock

data class TransformBlock(override val flag: Int, val npcId: Int) : UpdateBlock
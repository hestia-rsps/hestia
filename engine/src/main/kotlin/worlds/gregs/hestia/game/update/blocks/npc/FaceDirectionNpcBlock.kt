package worlds.gregs.hestia.game.update.blocks.npc

import worlds.gregs.hestia.game.update.UpdateBlock

data class FaceDirectionNpcBlock(override val flag: Int, val x: Int, val y: Int, val directionX: Int, val directionY: Int) : UpdateBlock
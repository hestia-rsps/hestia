package worlds.gregs.hestia.game.update.blocks.mob

import worlds.gregs.hestia.game.update.UpdateBlock


data class FaceDirectionMobBlock(override val flag: Int, val x: Int, val y: Int, val directionX: Int, val directionY: Int) : UpdateBlock
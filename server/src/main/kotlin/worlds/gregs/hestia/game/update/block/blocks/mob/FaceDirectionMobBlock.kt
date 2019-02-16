package worlds.gregs.hestia.game.update.block.blocks.mob

import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.block.UpdateBlock
import worlds.gregs.hestia.game.update.components.direction.Face

data class FaceDirectionMobBlock(override val flag: Int, val position: Position, val direction: Face) : UpdateBlock
package worlds.gregs.hestia.game.client.update.block.blocks.mob

import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.api.client.update.block.UpdateBlock
import worlds.gregs.hestia.api.client.update.components.direction.Face

data class FaceDirectionMobBlock(override val flag: Int, val position: Position, val direction: Face) : UpdateBlock
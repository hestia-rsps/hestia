package worlds.gregs.hestia.game.client.update.block.blocks.player

import worlds.gregs.hestia.api.client.update.block.UpdateBlock
import worlds.gregs.hestia.api.client.update.components.direction.Face

data class FaceDirectionPlayerBlock(override val flag: Int, val face: Face?) : UpdateBlock
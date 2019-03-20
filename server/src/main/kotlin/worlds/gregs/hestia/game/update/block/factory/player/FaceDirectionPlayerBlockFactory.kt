package worlds.gregs.hestia.game.update.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.direction.Face
import worlds.gregs.hestia.game.update.components.direction.Facing
import worlds.gregs.hestia.game.update.block.blocks.player.FaceDirectionPlayerBlock
import worlds.gregs.hestia.game.update.DirectionUtils
import worlds.gregs.hestia.services.Aspect

class FaceDirectionPlayerBlockFactory(flag: Int) : BlockFactory<FaceDirectionPlayerBlock>(Aspect.all(Renderable::class, Facing::class), true, flag = flag) {

    private lateinit var faceMapper: ComponentMapper<Face>

    override fun create(player: Int, other: Int): FaceDirectionPlayerBlock {
        val direction = faceMapper.get(other)
        return FaceDirectionPlayerBlock(flag, if (direction != null) DirectionUtils.getFaceDirection(direction.x, direction.y) else 0)
    }

}
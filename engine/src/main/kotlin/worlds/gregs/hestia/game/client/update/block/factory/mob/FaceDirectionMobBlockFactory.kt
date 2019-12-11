package worlds.gregs.hestia.game.client.update.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.api.client.update.components.direction.Face
import worlds.gregs.hestia.api.client.update.components.direction.Facing
import worlds.gregs.hestia.game.client.update.block.blocks.mob.FaceDirectionMobBlock
import worlds.gregs.hestia.services.Aspect

class FaceDirectionMobBlockFactory(flag: Int) : BlockFactory<FaceDirectionMobBlock>(Aspect.all(Renderable::class, Facing::class, Position::class), flag = flag, mob = true) {

    private lateinit var faceMapper: ComponentMapper<Face>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun create(player: Int, other: Int): FaceDirectionMobBlock {
        val position = positionMapper.get(other)
        val direction = faceMapper.get(other)
        return FaceDirectionMobBlock(flag, position, direction)
    }

}
package worlds.gregs.hestia.game.client.update.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.game.client.update.block.blocks.player.FaceDirectionPlayerBlock
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.components.direction.Face
import worlds.gregs.hestia.api.client.update.components.direction.Facing
import worlds.gregs.hestia.services.Aspect

class FaceDirectionPlayerBlockFactory(flag: Int) : BlockFactory<FaceDirectionPlayerBlock>(Aspect.all(Renderable::class, Facing::class), true, flag = flag) {

    private lateinit var faceMapper: ComponentMapper<Face>

    override fun create(player: Int, other: Int): FaceDirectionPlayerBlock {
        val face = faceMapper.get(other)
        return FaceDirectionPlayerBlock(flag, face)
    }

}
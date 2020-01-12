package worlds.gregs.hestia.core.display.update.logic.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.display.update.model.components.direction.FaceUpdate
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.game.update.blocks.mob.FaceDirectionMobBlock
import worlds.gregs.hestia.artemis.Aspect

class FaceDirectionMobBlockFactory(flag: Int) : BlockFactory<FaceDirectionMobBlock>(Aspect.all(Renderable::class, FaceUpdate::class, Position::class), flag = flag, mob = true) {

    private lateinit var facingMapper: ComponentMapper<Facing>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun create(player: Int, other: Int): FaceDirectionMobBlock {
        val position = positionMapper.get(other)
        val direction = facingMapper.get(other)
        return FaceDirectionMobBlock(flag, position.x, position.y, direction.x, direction.y)
    }

}
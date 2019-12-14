package worlds.gregs.hestia.core.display.update.logic.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.Transform
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.game.update.blocks.mob.TransformBlock
import worlds.gregs.hestia.service.Aspect

class TransformMobBlockFactory(flag: Int) : BlockFactory<TransformBlock>(Aspect.all(Renderable::class, Transform::class), flag = flag, mob = true) {

    private lateinit var transformMapper: ComponentMapper<Transform>
    private lateinit var typeMapper: ComponentMapper<Type>

    override fun create(player: Int, other: Int): TransformBlock {
        var mobId = transformMapper.get(other)?.mobId ?: -1
        if(mobId == -1) {
            mobId = typeMapper.get(other).id
        }
        return TransformBlock(flag, mobId)
    }

}
package worlds.gregs.hestia.game.client.update.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.api.client.update.components.Transform
import worlds.gregs.hestia.game.entity.components.Type
import worlds.gregs.hestia.game.client.update.block.blocks.mob.TransformBlock
import worlds.gregs.hestia.services.Aspect

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
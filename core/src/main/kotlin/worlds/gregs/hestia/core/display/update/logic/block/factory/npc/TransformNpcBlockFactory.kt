package worlds.gregs.hestia.core.display.update.logic.block.factory.npc

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.Transform
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.game.update.blocks.npc.TransformBlock

class TransformNpcBlockFactory(flag: Int) : BlockFactory<TransformBlock>(Aspect.all(Renderable::class, Transform::class), flag = flag, npc = true) {

    private lateinit var transformMapper: ComponentMapper<Transform>
    private lateinit var typeMapper: ComponentMapper<Type>

    override fun create(player: Int, other: Int): TransformBlock {
        var npcId = transformMapper.get(other)?.npcId ?: -1
        if(npcId == -1) {
            npcId = typeMapper.get(other).id
        }
        return TransformBlock(flag, npcId)
    }

}
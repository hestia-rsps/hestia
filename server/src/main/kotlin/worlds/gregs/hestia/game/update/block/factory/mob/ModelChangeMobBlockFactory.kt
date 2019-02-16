package worlds.gregs.hestia.game.update.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.ModelChange
import worlds.gregs.hestia.game.update.block.blocks.mob.ModelChangeBlock
import worlds.gregs.hestia.services.Aspect

class ModelChangeMobBlockFactory(flag: Int) : BlockFactory<ModelChangeBlock>(Aspect.all(Renderable::class, ModelChange::class), flag = flag, mob = true) {

    private lateinit var modelChangeMapper: ComponentMapper<ModelChange>

    override fun create(player: Int, other: Int): ModelChangeBlock {
        val modelChange = modelChangeMapper.get(other)
        return ModelChangeBlock(flag, modelChange.models, modelChange.colours, modelChange.textures)
    }

}
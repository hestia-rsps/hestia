package worlds.gregs.hestia.core.display.update.logic.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.ModelChange
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.game.update.blocks.mob.ModelChangeBlock
import worlds.gregs.hestia.artemis.Aspect

class ModelChangeMobBlockFactory(flag: Int) : BlockFactory<ModelChangeBlock>(Aspect.all(Renderable::class, ModelChange::class), flag = flag, mob = true) {

    private lateinit var modelChangeMapper: ComponentMapper<ModelChange>

    override fun create(player: Int, other: Int): ModelChangeBlock {
        val modelChange = modelChangeMapper.get(other)
        return ModelChangeBlock(flag, modelChange.models, modelChange.colours, modelChange.textures)
    }

}
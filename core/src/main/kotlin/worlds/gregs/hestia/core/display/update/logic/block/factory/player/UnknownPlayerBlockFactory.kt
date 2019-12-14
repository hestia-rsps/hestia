package worlds.gregs.hestia.core.display.update.logic.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.UpdateUnknown
import worlds.gregs.hestia.game.update.blocks.player.UnknownBlock
import worlds.gregs.hestia.artemis.Aspect

class UnknownPlayerBlockFactory(flag: Int) : BlockFactory<UnknownBlock>(Aspect.all(Renderable::class, UpdateUnknown::class), flag = flag) {

    private lateinit var updateUnknownMapper: ComponentMapper<UpdateUnknown>

    override fun create(player: Int, other: Int): UnknownBlock {
        return UnknownBlock(flag)
    }
}
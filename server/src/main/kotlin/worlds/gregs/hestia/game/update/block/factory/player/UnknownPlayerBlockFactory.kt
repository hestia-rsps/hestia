package worlds.gregs.hestia.game.update.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.UpdateUnknown
import worlds.gregs.hestia.game.update.block.blocks.player.UnknownBlock
import worlds.gregs.hestia.services.Aspect

class UnknownPlayerBlockFactory(flag: Int) : BlockFactory<UnknownBlock>(Aspect.all(Renderable::class, UpdateUnknown::class), flag = flag) {

    private lateinit var updateUnknownMapper: ComponentMapper<UpdateUnknown>

    override fun create(player: Int, other: Int): UnknownBlock {
        return UnknownBlock(flag)
    }
}
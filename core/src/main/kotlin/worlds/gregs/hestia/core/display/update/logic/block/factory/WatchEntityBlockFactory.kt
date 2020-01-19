package worlds.gregs.hestia.core.display.update.logic.block.factory

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.direction.Watching
import worlds.gregs.hestia.game.update.blocks.WatchEntityBlock

open class WatchEntityBlockFactory(flag: Int, npc: Boolean = false) : BlockFactory<WatchEntityBlock>(Aspect.all(Renderable::class, Watching::class), flag = flag, npc = npc) {

    private lateinit var watchingMapper: ComponentMapper<Watching>

    override fun create(player: Int, other: Int): WatchEntityBlock {
        return WatchEntityBlock(flag, watchingMapper.get(other)?.clientIndex ?: -1)
    }

}
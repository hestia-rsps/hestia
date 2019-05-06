package worlds.gregs.hestia.game.client.update.block.factory

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.api.client.update.components.direction.Watching
import worlds.gregs.hestia.game.client.update.block.blocks.WatchEntityBlock
import worlds.gregs.hestia.services.Aspect

open class WatchEntityBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<WatchEntityBlock>(Aspect.all(Renderable::class, Watching::class), flag = flag, mob = mob) {

    private lateinit var watchingMapper: ComponentMapper<Watching>

    override fun create(player: Int, other: Int): WatchEntityBlock {
        return WatchEntityBlock(flag, watchingMapper.get(other)?.clientIndex
                ?: -1)
    }

}
package worlds.gregs.hestia.game.update.block.factory

import com.artemis.ComponentMapper
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.TimeBar
import worlds.gregs.hestia.game.update.block.blocks.TimeBarBlock
import worlds.gregs.hestia.services.Aspect

open class TimeBarBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<TimeBarBlock>(Aspect.all(Renderable::class, TimeBar::class), flag = flag, mob = mob) {

    private lateinit var timeBarMapper: ComponentMapper<TimeBar>

    override fun create(player: Int, other: Int): TimeBarBlock {
        val timeBar = timeBarMapper.get(other)
        return TimeBarBlock(flag, (timeBar.full.int * 0x8000) or (timeBar.exponentialDelay and 0x7fff), timeBar.delay, timeBar.increment)
    }

}
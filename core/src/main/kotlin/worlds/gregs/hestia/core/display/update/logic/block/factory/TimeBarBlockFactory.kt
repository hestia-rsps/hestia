package worlds.gregs.hestia.core.display.update.logic.block.factory

import com.artemis.ComponentMapper
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.TimeBar
import worlds.gregs.hestia.game.update.blocks.TimeBarBlock

open class TimeBarBlockFactory(flag: Int, npc: Boolean = false) : BlockFactory<TimeBarBlock>(Aspect.all(Renderable::class, TimeBar::class), flag = flag, npc = npc) {

    private lateinit var timeBarMapper: ComponentMapper<TimeBar>

    override fun create(player: Int, other: Int): TimeBarBlock {
        val timeBar = timeBarMapper.get(other)
        return TimeBarBlock(flag, (timeBar.full.int * 0x8000) or (timeBar.exponentialDelay and 0x7fff), timeBar.delay, timeBar.increment)
    }

}
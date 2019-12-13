package worlds.gregs.hestia.core.task.tick

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.task.tick.logic.TickTaskSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY

class TickPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PRE_SYNC_PRIORITY, TickTaskSystem())
    }

}
package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugins.core.systems.TickTaskSystem

class CorePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PRE_SYNC_PRIORITY, TickTaskSystem())
    }

}
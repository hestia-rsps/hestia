package worlds.gregs.hestia.core.misc

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.core.misc.systems.TickTaskSystem
import worlds.gregs.hestia.core.misc.systems.cache.CacheSystem
import worlds.gregs.hestia.core.misc.systems.cache.ObjectDefinitionSystem

class CorePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(CacheSystem())
        b.with(ObjectDefinitionSystem())
        b.with(PRE_SYNC_PRIORITY, TickTaskSystem())
    }

}
package worlds.gregs.hestia.core

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.service.cache.systems.CacheSystem
import worlds.gregs.hestia.service.cache.systems.ObjectDefinitionSystem

class CachePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(CacheSystem())
        b.with(ObjectDefinitionSystem())
    }

}
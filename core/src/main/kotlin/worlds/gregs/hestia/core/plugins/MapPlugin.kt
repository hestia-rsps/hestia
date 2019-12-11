package worlds.gregs.hestia.core.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.core.plugins.map.systems.MapSettingsSystem
import worlds.gregs.hestia.core.plugins.map.systems.MapSystem
import worlds.gregs.hestia.core.plugins.map.systems.MapCollisionSystem

class MapPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(MapSystem(), MapSettingsSystem(), MapCollisionSystem())
    }

}
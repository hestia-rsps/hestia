package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem
import worlds.gregs.hestia.game.plugins.map.systems.MapSystem
import worlds.gregs.hestia.game.plugins.map.systems.MapCollisionSystem

class MapPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(MapSystem(), MapSettingsSystem(), MapCollisionSystem())
    }

}
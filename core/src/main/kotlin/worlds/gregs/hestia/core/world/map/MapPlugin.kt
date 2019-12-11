package worlds.gregs.hestia.core.world.map

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.core.world.map.systems.MapSettingsSystem
import worlds.gregs.hestia.core.world.map.systems.MapSystem
import worlds.gregs.hestia.core.world.map.systems.MapCollisionSystem

class MapPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(MapSystem(), MapSettingsSystem(), MapCollisionSystem())
    }

}
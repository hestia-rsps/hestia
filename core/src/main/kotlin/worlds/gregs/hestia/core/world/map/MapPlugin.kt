package worlds.gregs.hestia.core.world.map

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.world.map.api.container.NpcMap
import worlds.gregs.hestia.core.world.map.api.container.PlayerMap
import worlds.gregs.hestia.core.world.map.api.container.PlayerUpdateSystem
import worlds.gregs.hestia.core.world.map.logic.systems.MapCollisionSystem
import worlds.gregs.hestia.core.world.map.logic.systems.MapSettingsSystem
import worlds.gregs.hestia.core.world.map.logic.systems.MapSystem
import worlds.gregs.hestia.game.plugin.Plugin

class MapPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(MapSystem(), MapSettingsSystem(), MapCollisionSystem())
        b.with(NpcMap(), PlayerMap())
        b.with(Plugin.POST_SHIFT_PRIORITY, PlayerUpdateSystem())
    }

}
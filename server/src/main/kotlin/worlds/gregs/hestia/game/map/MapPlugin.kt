package worlds.gregs.hestia.game.map

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.map.container.MobMap
import worlds.gregs.hestia.game.map.container.PlayerMap
import worlds.gregs.hestia.game.map.container.PlayerUpdateSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_SHIFT_PRIORITY

//TODO temp until all plugins are in server
class MapPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(MobMap(), PlayerMap())
        b.with(POST_SHIFT_PRIORITY, PlayerUpdateSystem())
    }

}
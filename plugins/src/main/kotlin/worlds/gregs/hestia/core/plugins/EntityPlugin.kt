package worlds.gregs.hestia.core.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_FINISH_PRIORITY
import worlds.gregs.hestia.core.plugins.entity.systems.sync.PostEntitySyncSystem
import worlds.gregs.hestia.core.plugins.entity.systems.update.*
import worlds.gregs.hestia.core.plugins.player.systems.update.AppearanceSystem

class EntityPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(DamageSystem(), AnimationSystem(), GraphicsSystem(), AppearanceSystem())
        b.with(PRE_SYNC_PRIORITY, WatchingSystem())
        b.with(PRE_SHIFT_PRIORITY, DirectionSystem())
        b.with(UPDATE_FINISH_PRIORITY, PostEntitySyncSystem())
    }

}
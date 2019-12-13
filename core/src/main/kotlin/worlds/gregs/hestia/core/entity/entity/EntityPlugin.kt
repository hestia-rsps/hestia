package worlds.gregs.hestia.core.entity.entity

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.entity.logic.systems.update.*
import worlds.gregs.hestia.core.entity.player.logic.systems.update.AppearanceSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY

class EntityPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(DamageSystem(), AnimationSystem(), GraphicsSystem(), AppearanceSystem())
        b.with(PRE_SYNC_PRIORITY, WatchingSystem())
        b.with(PRE_SHIFT_PRIORITY, DirectionSystem())
    }

}
package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.ENTITY_INDEX_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_FINISH_PRIORITY
import worlds.gregs.hestia.game.plugins.entity.systems.sync.EntityIndexSystem
import worlds.gregs.hestia.game.plugins.entity.systems.sync.PostEntitySyncSystem
import worlds.gregs.hestia.game.plugins.entity.systems.update.*
import worlds.gregs.hestia.game.plugins.entity.systems.update.stage.EntityStageSystem
import worlds.gregs.hestia.game.plugins.player.systems.update.AppearanceSystem

class EntityPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(DamageSystem(), AnimationSystem(), GraphicsSystem(), AppearanceSystem(), EntitySyncHandlers(), EntityStageSystem())
        b.with(ENTITY_INDEX_PRIORITY, EntityIndexSystem())
        b.with(PRE_SYNC_PRIORITY, DirectionSystem(), WatchingSystem())
        b.with(SHIFT_PRIORITY + 1, MovementFaceSystem())
        b.with(UPDATE_FINISH_PRIORITY, PostEntitySyncSystem())
    }

}
package world.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import world.gregs.hestia.game.plugin.Plugin
import world.gregs.hestia.game.plugin.Plugin.Companion.MOB_SYNC_PRIORITY
import world.gregs.hestia.game.plugin.Plugin.Companion.POST_SYNC_PRIORITY
import world.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import world.gregs.hestia.game.plugin.Plugin.Companion.SHIFT_PRIORITY
import world.gregs.hestia.game.plugin.Plugin.Companion.SYNC_PRIORITY
import world.gregs.hestia.game.systems.*
import world.gregs.hestia.game.systems.direction.DirectionSystem
import world.gregs.hestia.game.systems.direction.MovementFaceSystem
import world.gregs.hestia.game.systems.direction.WatchingSystem
import world.gregs.hestia.game.systems.login.*
import world.gregs.hestia.game.systems.sync.MobSyncSystem
import world.gregs.hestia.game.systems.sync.PlayerSyncSystem
import world.gregs.hestia.game.systems.sync.PostSyncSystem

class PlayerPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        //Direction
        b.with(SHIFT_PRIORITY + 1, MovementFaceSystem())
        b.with(PRE_SYNC_PRIORITY, DirectionSystem(), WatchingSystem())

        //Sync
        b.with(SYNC_PRIORITY, PlayerSyncSystem())
        b.with(MOB_SYNC_PRIORITY, MobSyncSystem())
        b.with(POST_SYNC_PRIORITY, PostSyncSystem())

        b.with(PRE_SYNC_PRIORITY, TickTaskSystem())

        //Login
        b.with(PlayerChangeSystem(), ClientIndexSystem(), RegionSystem(), PlayerLoginSystem(), MapRegionSystem(), AppearanceSystem(), InterfaceSystem())
        b.with(DamageSystem(), AnimationSystem(), GraphicsSystem())
    }

}
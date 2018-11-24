package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.player.systems.sync.*
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.MOB_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.SYNC_PRIORITY
import worlds.gregs.hestia.game.systems.*
import worlds.gregs.hestia.game.systems.direction.DirectionSystem
import worlds.gregs.hestia.game.systems.direction.MovementFaceSystem
import worlds.gregs.hestia.game.systems.direction.WatchingSystem
import worlds.gregs.hestia.game.systems.login.*
import worlds.gregs.hestia.game.systems.sync.*
import worlds.gregs.hestia.game.mob.systems.sync.MobSyncSystem
import worlds.gregs.hestia.game.mob.systems.sync.MobChunkSystem
import worlds.gregs.hestia.game.mob.systems.sync.MobIndexSystem
import worlds.gregs.hestia.game.mob.systems.sync.MobViewDistanceSystem

class PlayerPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PlayerChunkMap())

        //Direction
        b.with(SHIFT_PRIORITY + 1, MovementFaceSystem(), PlayerChunkChangeSystem())
        b.with(PRE_SYNC_PRIORITY, RegionLoadSystem())
        b.with(PRE_SYNC_PRIORITY, DirectionSystem(), WatchingSystem())

        //Sync
        b.with(SYNC_PRIORITY, PlayerSyncSystem())
        b.with(MOB_SYNC_PRIORITY, MobSyncSystem())
        b.with(POST_SYNC_PRIORITY, PostSyncSystem())
        b.with(PRE_SYNC_PRIORITY, PlayerViewDistanceSystem(), MobViewDistanceSystem())

        b.with(PRE_SYNC_PRIORITY, TickTaskSystem())

        b.with(POST_SYNC_PRIORITY, PlayerChunkSystem(), MobChunkSystem())
        //Login
        b.with(ViewportSystem(), PlayerIndexSystem(), MobIndexSystem(), PlayerLoginSystem(), MapRegionSystem(), AppearanceSystem(), InterfaceSystem())
        b.with(DamageSystem(), AnimationSystem(), GraphicsSystem())
        b.with(PlayerLogoutSystem())
    }

}
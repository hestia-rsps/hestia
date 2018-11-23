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
import world.gregs.hestia.game.systems.sync.*
import world.gregs.hestia.game.systems.sync.mob.MobSyncSystem
import world.gregs.hestia.game.systems.sync.player.PlayerSyncSystem
import world.gregs.hestia.game.systems.sync.mob.MobChunkSystem
import world.gregs.hestia.game.systems.sync.mob.MobIndexSystem
import world.gregs.hestia.game.systems.sync.mob.MobViewDistanceSystem
import world.gregs.hestia.game.systems.sync.player.PlayerChunkSystem
import world.gregs.hestia.game.systems.sync.player.PlayerIndexSystem
import world.gregs.hestia.game.systems.sync.player.PlayerViewDistanceSystem

class PlayerPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PlayerChunkMap(), MobChunkMap())

        //Direction
        b.with(SHIFT_PRIORITY + 1, MovementFaceSystem(), PlayerChunkChangeSystem(), MobChunkChangeSystem())
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
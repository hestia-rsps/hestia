package worlds.gregs.hestia.game.update

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_SYNC_PRIORITY
import worlds.gregs.hestia.game.update.block.factory.*
import worlds.gregs.hestia.game.update.block.factory.mob.*
import worlds.gregs.hestia.game.update.block.factory.player.*
import worlds.gregs.hestia.game.update.sync.MobSynchronize
import worlds.gregs.hestia.game.update.sync.PlayerSynchronize
import worlds.gregs.hestia.game.update.sync.ViewDistanceSystem
import worlds.gregs.hestia.game.update.sync.mob.global.AddMobSyncFactory
import worlds.gregs.hestia.game.update.sync.mob.local.MobMovementSyncFactory
import worlds.gregs.hestia.game.update.sync.mob.local.RemoveMobSyncFactory
import worlds.gregs.hestia.game.update.sync.mob.local.UpdateMobSyncFactory
import worlds.gregs.hestia.game.update.sync.player.global.AddPlayerSyncFactory
import worlds.gregs.hestia.game.update.sync.player.global.HeightSyncFactory
import worlds.gregs.hestia.game.update.sync.player.global.MoveGlobalSyncFactory
import worlds.gregs.hestia.game.update.sync.player.global.RegionSyncFactory
import worlds.gregs.hestia.game.update.sync.player.local.MoveLocalSyncFactory
import worlds.gregs.hestia.game.update.sync.player.local.PlayerMovementSyncFactory
import worlds.gregs.hestia.game.update.sync.player.local.RemovePlayerSyncFactory
import worlds.gregs.hestia.game.update.sync.player.local.UpdatePlayerSyncFactory

class ClientUpdatePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PRE_SYNC_PRIORITY, ViewDistanceSystem())
        b.with(UPDATE_SYNC_PRIORITY, PlayerSynchronize(), MobSynchronize())

        //Local player view changes
        b.with(RemovePlayerSyncFactory(), MoveLocalSyncFactory(), PlayerMovementSyncFactory(), UpdatePlayerSyncFactory())
        //Global player view changes
        b.with(AddPlayerSyncFactory(), HeightSyncFactory(), RegionSyncFactory(), MoveGlobalSyncFactory())

        //Local mob view changes
        b.with(RemoveMobSyncFactory(), UpdateMobSyncFactory(), MobMovementSyncFactory())
        //Global mob view changes
        b.with(AddMobSyncFactory())

        //Player update blocks
        b.with(
                BatchAnimationBlockFactory(0x8000),
                AnimationBlockFactory(0x40),
                GraphicThreeBlockFactory(0x40000),
                ColourOverlayBlockFactory(0x20000),
                MoveTypePlayerBlockFactory(0x200),
                TimeBarBlockFactory(0x2000),
                GraphicFourBlockFactory(0x80000),
                ClanMemberPlayerBlockFactory(0x100000),
                HitsBlockFactory(0x4),
                UnknownPlayerBlockFactory(0x10000),
                AppearancePlayerBlockFactory(0x8),
                ForceChatBlockFactory(0x4000),
                MiniMapPlayerBlockFactory(0x400),
                MovementPlayerBlockFactory(0x1),
                WatchEntityBlockFactory(0x10),
                ForceMovementBlockFactory(0x1000),
                FaceDirectionPlayerBlockFactory(0x20),
                GraphicOneBlockFactory(0x2),
                GraphicTwoBlockFactory(0x100)
        )

        //Mob update blocks
        b.with(
                object : GraphicThreeBlockFactory(0x100000, true) {},
                object : WatchEntityBlockFactory(0x1, true) {},
                object : GraphicFourBlockFactory(0x20000, true) {},
                object : HitsBlockFactory(0x40, true) {},
                object : TimeBarBlockFactory(0x100, true) {},
                NameMobBlockFactory(0x40000),
                TransformMobBlockFactory(0x20),
                object : ForceChatBlockFactory(0x2, true) {},
                FaceDirectionMobBlockFactory(0x8),
                CombatLevelMobBlockFactory(0x80000),
                //0x2000 - Weapon hidden/rotation
                //0x10000 - Model change (not sure it actually does anything)
                object : ForceMovementBlockFactory(0x400, true) {},
                object : AnimationBlockFactory(0x10, true) {},
                ModelChangeMobBlockFactory(0x800),
                object : BatchAnimationBlockFactory(0x4000, true) {},
                object : GraphicTwoBlockFactory(0x1000, true) {},
                object : GraphicOneBlockFactory(0x4, true) {},
                object : ColourOverlayBlockFactory(0x200, true) {}
        )
    }

}
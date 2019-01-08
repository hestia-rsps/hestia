package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.LOGIN_DETAILS_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.MAP_REGION_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_CHANGE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_DISPLAY_FLAG_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_FINISH_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_FLAG_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_SYNC_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.UPDATE_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugins.client.systems.network.*
import worlds.gregs.hestia.game.plugins.client.systems.network.`in`.*
import worlds.gregs.hestia.game.plugins.client.systems.region.ClientRegionChangeSystem
import worlds.gregs.hestia.game.plugins.client.systems.region.ClientRegionLoadSystem
import worlds.gregs.hestia.game.plugins.client.systems.region.RegionSenderSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.GlobalEntitySystem
import worlds.gregs.hestia.game.plugins.client.systems.update.PostUpdateSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.stage.GlobalDisplayFlagSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.stage.LocalDisplayFlagSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.sync.MobSyncSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.MobUpdateSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.PlayerUpdateSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.change.MobUpdateChangeSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.change.PlayerUpdateChangeSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.flag.MobUpdateFlagSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.flag.PlayerUpdateFlagSystem
import worlds.gregs.hestia.game.plugins.movement.systems.update.MovementStageChecks

class ClientPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(CommandHandler(), InterfaceHandler(), WalkingHandler(), ScreenHandler(), CloseInterfaceHandler(), WorldMapHandler(), DialogueContinueHandler())

        b.with(LOGIN_DETAILS_PRIORITY, ClientConnectSystem())
        b.with(PacketSystem(), PacketSender(), ClientDisconnectSystem(), GlobalEntitySystem(), ClientNetworkSystem())
        b.with(UPDATE_FLAG_PRIORITY, PlayerUpdateFlagSystem(), MobUpdateFlagSystem())
        b.with(UPDATE_DISPLAY_FLAG_PRIORITY, LocalDisplayFlagSystem(), GlobalDisplayFlagSystem(), MovementStageChecks())
        b.with(UPDATE_SYNC_PRIORITY, PlayerSyncSystem(), MobSyncSystem())
        b.with(UPDATE_UPDATE_PRIORITY, PlayerUpdateSystem(), MobUpdateSystem())
        b.with(UPDATE_CHANGE_PRIORITY, PlayerUpdateChangeSystem(), MobUpdateChangeSystem())
        b.with(UPDATE_FINISH_PRIORITY, PostUpdateSystem())
        b.with(MAP_REGION_PRIORITY, ClientRegionChangeSystem(), RegionSenderSystem())
        b.with(PRE_SYNC_PRIORITY, ClientRegionLoadSystem())
    }

}
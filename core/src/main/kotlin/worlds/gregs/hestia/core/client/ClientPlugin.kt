package worlds.gregs.hestia.core.client

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.client.systems.network.ClientConnectSystem
import worlds.gregs.hestia.core.client.systems.network.ClientDisconnectSystem
import worlds.gregs.hestia.core.client.systems.network.ClientNetworkSystem
import worlds.gregs.hestia.core.client.systems.network.PacketSender
import worlds.gregs.hestia.core.client.systems.network.`in`.*
import worlds.gregs.hestia.core.client.systems.region.ClientRegionChangeSystem
import worlds.gregs.hestia.core.client.systems.region.ClientRegionLoadSystem
import worlds.gregs.hestia.core.client.systems.region.RegionSenderSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.LOGIN_DETAILS_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.MAP_REGION_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SYNC_PRIORITY

class ClientPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(ConsoleCommandHandler(), WidgetOptionHandler(), WalkTargetHandler(), ScreenChangeHandler(), ScreenCloseHandler(), WorldMapOpenHandler(), DialogueContinueHandler(), StringEntryHandler(), IntegerEntryHandler())

        b.with(LOGIN_DETAILS_PRIORITY, ClientConnectSystem())
        b.with(ClientDisconnectSystem(), ClientNetworkSystem())
//        b.with(UPDATE_DISPLAY_FLAG_PRIORITY, LocalDisplayFlagSystem(), GlobalDisplayFlagSystem(), MovementStageChecks())
//        b.with(UPDATE_SYNC_PRIORITY, MobSyncSystem())
//        b.with(UPDATE_CHANGE_PRIORITY, MobUpdateChangeSystem())
        b.with(MAP_REGION_PRIORITY, ClientRegionChangeSystem(), RegionSenderSystem())
        b.with(PRE_SYNC_PRIORITY, ClientRegionLoadSystem())
        b.with(POST_UPDATE_PRIORITY, PacketSender())
    }

}
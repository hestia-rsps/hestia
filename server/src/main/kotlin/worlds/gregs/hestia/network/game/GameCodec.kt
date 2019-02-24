package worlds.gregs.hestia.network.game

import world.gregs.hestia.core.network.codec.MessageHandshakeCodec
import world.gregs.hestia.core.network.protocol.decoders.LoginHandshakeDecoder
import world.gregs.hestia.core.network.protocol.decoders.PingDecoder
import world.gregs.hestia.core.network.protocol.encoders.*
import worlds.gregs.hestia.network.game.decoders.*
import worlds.gregs.hestia.network.game.encoders.*

class GameCodec : MessageHandshakeCodec() {
    init {
        bind(GameLoginDecoder(), true)
        bind(LoginHandshakeDecoder(), true)

        bind(PingDecoder())
        bind(ConsoleCommandDecoder())
        bind(DialogueContinueDecoder())
        bind(ScreenChangeDecoder())
        bind(StringEntryDecoder())
        bind(WalkTargetDecoder())
        bind(WidgetClickDecoder())
        bind(WorldMapOpenDecoder())
        bind(ScreenCloseDecoder())

        bind(ClientResponseEncoder())
        bind(WidgetComponentSettingsEncoder())
        bind(ConfigEncoder())
        bind(ConfigFileEncoder())
        bind(ConfigGlobalEncoder())
        bind(FriendListDisconnectEncoder())
        bind(LoginDetailsEncoder())
        bind(LogoutEncoder())
        bind(MapRegionDynamicEncoder())
        bind(MapRegionEncoder())
        bind(RunEnergyEncoder())
        bind(ScriptEncoder())
        bind(SkillLevelEncoder())
        bind(WidgetCloseEncoder())
        bind(WidgetComponentAnimationEncoder())
        bind(WidgetComponentTextEncoder())
        bind(WidgetHeadMobEncoder())
        bind(WidgetHeadPlayerEncoder())
        bind(WidgetItemEncoder())
        bind(WidgetItemsEncoder())
        bind(WidgetOpenEncoder())
        bind(WidgetWindowsPaneEncoder())

        bind(ChatEncoder())
        bind(FriendsChatDisconnectEncoder())
        bind(FriendListUnlockEncoder())
    }
}
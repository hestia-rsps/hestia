package worlds.gregs.hestia.network.world

import world.gregs.hestia.core.network.codec.MessageCodec
import world.gregs.hestia.core.network.protocol.decoders.FriendsChatSetupRequestDecoder
import world.gregs.hestia.core.network.protocol.encoders.FriendsChatSetupRequestEncoder
import worlds.gregs.hestia.network.world.decoders.ClientMessageDecoder
import worlds.gregs.hestia.network.world.decoders.PlayerLoginResponseDecoder
import worlds.gregs.hestia.network.world.decoders.PlayerLoginSuccessDecoder
import worlds.gregs.hestia.network.world.decoders.SocialDetailsDecoder
import worlds.gregs.hestia.network.world.encoders.*

class WorldCodec(decoders: ArrayList<Triple<Int, Int, Boolean>>) : MessageCodec() {
    init {
        bind(FriendsChatSetupRequestDecoder())
        bind(PlayerLoginResponseDecoder())
        bind(PlayerLoginSuccessDecoder())
        bind(ClientMessageDecoder())
        bind(SocialDetailsDecoder(decoders))

        bind(FriendsChatSetupRequestEncoder())
        bind(WorldInfoEncoder())
        bind(PlayerLoginRequestEncoder())
        bind(PlayerLoginEncoder())
        bind(ClientPacketEncoder())
        bind(FriendsChatNameEncoder())
        bind(FriendsChatSettingsEncoder())
        bind(PlayerLogoutEncoder())
        bind(PlayerReconnectEncoder())
    }
}
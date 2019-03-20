package worlds.gregs.hestia.network.world

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.Codec
import world.gregs.hestia.core.network.codec.MessageDispatcher
import world.gregs.hestia.core.network.protocol.Details
import worlds.gregs.hestia.network.world.handlers.*

class WorldMessages(worldDetails: Details, sessions: HashMap<Int, ChannelHandlerContext>, codec: Codec) : MessageDispatcher() {
    init {
        bind(ClientPacketHandler())
        bind(ClientMessageHandler())
        bind(PlayerLoginResponseHandler(sessions))
        bind(PlayerLoginSuccessHandler(sessions, codec))
        bind(WorldDetailsHandler(worldDetails))
        bind(WorldPacketHandler())
    }
}
package worlds.gregs.hestia.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSetupRequest
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.events.FriendsChatSetupOpen

class WorldPacketHandler : MessageHandler<FriendsChatSetupRequest> {

    override fun handle(ctx: ChannelHandlerContext, message: FriendsChatSetupRequest) {
        val (entity) = message
        GameServer.eventSystem.dispatch(FriendsChatSetupOpen(entity))
    }

}
package worlds.gregs.hestia.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import worlds.gregs.hestia.GameServer.Companion.eventSystem
import worlds.gregs.hestia.game.events.OutBoundPacket
import worlds.gregs.hestia.network.world.decoders.messages.ClientPacketOut

class ClientMessageHandler : MessageHandler<ClientPacketOut> {

    override fun handle(ctx: ChannelHandlerContext, message: ClientPacketOut) {
        val (entity, packet) = message
        //No need for opcode and type as packet has already been encoded
        eventSystem.dispatch(OutBoundPacket(entity, packet))
    }

}
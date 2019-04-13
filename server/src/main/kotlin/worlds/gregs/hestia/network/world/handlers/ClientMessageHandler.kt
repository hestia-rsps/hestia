package worlds.gregs.hestia.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import worlds.gregs.hestia.GameServer.Companion.eventSystem
import worlds.gregs.hestia.artemis.events.OutBoundMessage
import worlds.gregs.hestia.network.world.decoders.messages.ClientPacketOut
import worlds.gregs.hestia.network.client.encoders.messages.ClientMessageOut

class ClientMessageHandler : MessageHandler<ClientPacketOut> {

    override fun handle(ctx: ChannelHandlerContext, message: ClientPacketOut) {
        val (entity, packet) = message
        //We extract opcode to encode with potential cipher other than that type and packet has already been encoded
        eventSystem.dispatch(OutBoundMessage(entity, ClientMessageOut(packet.readSmart(), packet)))
    }

}
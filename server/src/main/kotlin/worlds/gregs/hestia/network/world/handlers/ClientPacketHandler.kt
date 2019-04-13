package worlds.gregs.hestia.network.world.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.protocol.messages.ClientPacket
import worlds.gregs.hestia.GameServer.Companion.eventSystem
import worlds.gregs.hestia.artemis.events.OutBoundPacket

class ClientPacketHandler : MessageHandler<ClientPacket> {

    override fun handle(ctx: ChannelHandlerContext, message: ClientPacket) {
        val (entity, packet) = message
        //No need for opcode and type as packet has already been encoded
        eventSystem.dispatch(OutBoundPacket(entity, packet))
    }

}
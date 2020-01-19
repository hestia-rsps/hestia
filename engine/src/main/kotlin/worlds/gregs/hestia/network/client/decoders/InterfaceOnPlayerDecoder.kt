package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_ON_PLAYER
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnPlayer

class InterfaceOnPlayerDecoder : MessageDecoder<InterfaceOnPlayer>(11, INTERFACE_ON_PLAYER) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): InterfaceOnPlayer? {
        return InterfaceOnPlayer(packet.readShort(order = Endian.LITTLE), packet.readInt(order = Endian.LITTLE), packet.readShort(), packet.readBoolean(Modifier.SUBTRACT), packet.readShort(Modifier.ADD, Endian.LITTLE))
    }

}
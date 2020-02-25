package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.SWITCH_INTERFACE_COMPONENTS
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceSwitchComponents

class InterfaceSwitchComponentsDecoder : MessageDecoder<InterfaceSwitchComponents>(16, SWITCH_INTERFACE_COMPONENTS) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): InterfaceSwitchComponents? {
        return InterfaceSwitchComponents(packet.readShort(), packet.readShort(order = Endian.LITTLE), packet.readShort(Modifier.ADD), packet.readInt(order = Endian.MIDDLE), packet.readShort(order = Endian.LITTLE), packet.readInt(Modifier.INVERSE, Endian.MIDDLE))
    }

}
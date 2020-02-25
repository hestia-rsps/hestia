package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.ITEM_ON_ITEM
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnInterface

class InterfaceOnInterfaceDecoder : MessageDecoder<InterfaceOnInterface>(16, ITEM_ON_ITEM) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): InterfaceOnInterface? {
        return InterfaceOnInterface(packet.readInt(order = Endian.MIDDLE), packet.readShort(Modifier.ADD), packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readInt(Modifier.INVERSE, Endian.MIDDLE), packet.readShort(Modifier.ADD), packet.readShort(order = Endian.LITTLE))
    }

}
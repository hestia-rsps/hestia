package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.AP_COORD_T
import world.gregs.hestia.io.Modifier
import worlds.gregs.hestia.network.client.decoders.messages.APCoordinate

class APCoordinateDecoder : MessageDecoder<APCoordinate>(12, AP_COORD_T) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): APCoordinate? {
        return APCoordinate(packet.readShort(Modifier.ADD), packet.readShort(order = Endian.LITTLE), packet.readInt(order = Endian.MIDDLE), packet.readShort(Modifier.ADD), packet.readShort())
    }

}
package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MOVE_CAMERA
import worlds.gregs.hestia.network.client.decoders.messages.MovedCamera

class MovedCameraDecoder : MessageDecoder<MovedCamera>(4, MOVE_CAMERA) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): MovedCamera? {
        return MovedCamera(packet.readUnsignedShort(), packet.readUnsignedShort())
    }

}
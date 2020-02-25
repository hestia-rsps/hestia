package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.OTHER_TELEPORT
import worlds.gregs.hestia.network.client.decoders.messages.SecondaryTeleport

class SecondaryTeleportDecoder : MessageDecoder<SecondaryTeleport>(4, OTHER_TELEPORT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): SecondaryTeleport? {
        return SecondaryTeleport(packet.readShort(Modifier.ADD, Endian.LITTLE), packet.readShort(order = Endian.LITTLE))
    }

}
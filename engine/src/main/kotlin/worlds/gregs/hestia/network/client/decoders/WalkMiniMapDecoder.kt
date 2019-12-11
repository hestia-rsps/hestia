package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MINI_MAP_WALK
import worlds.gregs.hestia.network.client.decoders.messages.WalkMap

class WalkMiniMapDecoder : MessageDecoder<WalkMap>(18, MINI_MAP_WALK) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WalkMap? {
        val baseX = packet.readShort(Modifier.ADD, Endian.LITTLE)
        val baseY= packet.readShort(Modifier.ADD, Endian.LITTLE)
        val running = packet.readBoolean()
        packet.readByte()//-1
        packet.readByte()//-1
        packet.readShort()//Rotation?
        packet.readByte()//57
        val minimapRotation = packet.readByte()
        val minimapZoom = packet.readByte()
        packet.readByte()//89
        packet.readShort()//X in region?
        packet.readShort()//Y in region?
        packet.readByte()//63
        return WalkMap(baseX, baseY, running)
    }

}
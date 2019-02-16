package worlds.gregs.hestia.network.game.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MINI_MAP_WALK
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WALK
import worlds.gregs.hestia.network.game.decoders.messages.WalkTarget

class WalkTargetDecoder : MessageDecoder<WalkTarget>(Packet.Type.VAR_BYTE, WALK, MINI_MAP_WALK) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): WalkTarget? {
        val size = packet.readableBytes() - if(packet.opcode == MINI_MAP_WALK) 13 else 0
        val baseX = packet.readShort(Modifier.ADD, Endian.LITTLE)
        val baseY= packet.readShort(Modifier.ADD, Endian.LITTLE)
        val running = packet.readBoolean()
        val steps = Math.min((size - 5) / 2, 25)

        if (steps <= 0) {
            return null
        }

        val route = ArrayList<Pair<Int, Int>>()
        for (step in 0 until steps) {
            route.add(Pair(packet.readUnsignedByte(), packet.readUnsignedByte()))
        }
        return WalkTarget(baseX, baseY, route, running)
    }

}
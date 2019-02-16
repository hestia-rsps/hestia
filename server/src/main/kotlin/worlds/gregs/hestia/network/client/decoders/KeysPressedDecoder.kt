package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.KEY_TYPED
import worlds.gregs.hestia.network.client.decoders.messages.KeysPressed

class KeysPressedDecoder : MessageDecoder<KeysPressed>(Packet.Type.VAR_BYTE, KEY_TYPED) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): KeysPressed? {
        val keys = ArrayList<Pair<Int, Int>>()
        while(packet.buffer.isReadable) {
            keys.add(Pair(packet.readUnsignedByte(), packet.readUnsignedShort()))
        }
        return KeysPressed(keys)
    }

}
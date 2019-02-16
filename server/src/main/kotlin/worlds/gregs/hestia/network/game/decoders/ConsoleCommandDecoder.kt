package worlds.gregs.hestia.network.game.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.CONSOLE_COMMAND
import worlds.gregs.hestia.network.game.decoders.messages.ConsoleCommand

class ConsoleCommandDecoder : MessageDecoder<ConsoleCommand>(Packet.Type.VAR_BYTE, CONSOLE_COMMAND) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ConsoleCommand? {
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        return ConsoleCommand(packet.readString())
    }

}
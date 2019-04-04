package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_1
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_10
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_2
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_3
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_4
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_5
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_6
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_7
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_8
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION_9
import worlds.gregs.hestia.network.client.decoders.messages.PlayerOption

class PlayerOptionDecoder : MessageDecoder<PlayerOption>(3, PLAYER_OPTION_1, PLAYER_OPTION_2, PLAYER_OPTION_3, PLAYER_OPTION_4, PLAYER_OPTION_5, PLAYER_OPTION_6, PLAYER_OPTION_7, PLAYER_OPTION_8, PLAYER_OPTION_9, PLAYER_OPTION_10) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): PlayerOption? {
        packet.readByte()//0
        return PlayerOption(packet.readShort(), opcodes.indexOf(packet.opcode) + 1)
    }

}
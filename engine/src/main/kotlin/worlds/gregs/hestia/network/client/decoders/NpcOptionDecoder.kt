package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.NPC_OPTION_1
import world.gregs.hestia.core.network.protocol.ClientOpcodes.NPC_OPTION_2
import world.gregs.hestia.core.network.protocol.ClientOpcodes.NPC_OPTION_3
import world.gregs.hestia.core.network.protocol.ClientOpcodes.NPC_OPTION_4
import world.gregs.hestia.core.network.protocol.ClientOpcodes.NPC_OPTION_5
import world.gregs.hestia.core.network.protocol.ClientOpcodes.NPC_OPTION_6
import worlds.gregs.hestia.network.client.decoders.messages.NpcOptionMessage

class NpcOptionDecoder : MessageDecoder<NpcOptionMessage>(3, NPC_OPTION_1, NPC_OPTION_2, NPC_OPTION_3, NPC_OPTION_4, NPC_OPTION_5, NPC_OPTION_6) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): NpcOptionMessage? {
        return NpcOptionMessage(packet.readBoolean(Modifier.ADD), packet.readShort(Modifier.ADD), opcodes.indexOf(packet.opcode) + 1)
    }

}
package worlds.gregs.hestia.network.client.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MOB_OPTION_1
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MOB_OPTION_2
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MOB_OPTION_3
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MOB_OPTION_4
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MOB_OPTION_5
import world.gregs.hestia.core.network.protocol.ClientOpcodes.MOB_OPTION_6
import worlds.gregs.hestia.network.client.decoders.messages.MobOption

class MobOptionDecoder : MessageDecoder<MobOption>(3, MOB_OPTION_1, MOB_OPTION_2, MOB_OPTION_3, MOB_OPTION_4, MOB_OPTION_5, MOB_OPTION_6) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): MobOption? {
        return MobOption(packet.readBoolean(Modifier.ADD), packet.readShort(Modifier.ADD), opcodes.indexOf(packet.opcode) + 1)
    }

}
package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.PLAYER_OPTION
import worlds.gregs.hestia.network.client.encoders.messages.PlayerContextMenuOption

class PlayerContextMenuOptionEncoder : MessageEncoder<PlayerContextMenuOption>() {

    override fun encode(builder: PacketBuilder, message: PlayerContextMenuOption) {
        val (option, slot, top, cursor) = message
        builder.apply {
            writeOpcode(PLAYER_OPTION, Packet.Type.VAR_BYTE)
            writeByte(top, Modifier.ADD)
            writeShort(cursor, order = Endian.LITTLE)
            writeString(option)
            writeByte(slot, Modifier.INVERSE)
        }
    }

}
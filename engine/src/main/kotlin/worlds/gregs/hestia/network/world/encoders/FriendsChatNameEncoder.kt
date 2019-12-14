package worlds.gregs.hestia.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.FRIENDS_CHAT_NAME
import world.gregs.hestia.core.network.protocol.messages.FriendsChatName

class FriendsChatNameEncoder : MessageEncoder<FriendsChatName>() {

    override fun encode(builder: PacketBuilder, message: FriendsChatName) {
        val (entity, name) = message
        builder.apply {
            writeOpcode(FRIENDS_CHAT_NAME, Packet.Type.VAR_BYTE)
            writeInt(entity)
            writeString(name)
        }
    }

}
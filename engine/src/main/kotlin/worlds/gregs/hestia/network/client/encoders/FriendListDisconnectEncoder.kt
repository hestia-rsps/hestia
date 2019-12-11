package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.FRIEND_LIST_DISCONNECT
import worlds.gregs.hestia.network.client.encoders.messages.FriendListDisconnect

class FriendListDisconnectEncoder : MessageEncoder<FriendListDisconnect>() {

    override fun encode(builder: PacketBuilder, message: FriendListDisconnect) {
        builder.writeOpcode(FRIEND_LIST_DISCONNECT)
    }

}
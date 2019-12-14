package worlds.gregs.hestia.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.PLAYER_RECONNECT
import world.gregs.hestia.core.network.protocol.messages.PlayerReconnect

class PlayerReconnectEncoder : MessageEncoder<PlayerReconnect>() {

    override fun encode(builder: PacketBuilder, message: PlayerReconnect) {
        val (username, entity, client) = message
        builder.apply {
            writeOpcode(PLAYER_RECONNECT, Packet.Type.VAR_BYTE)
            writeString(username)
            writeInt(entity)
            writeShort(client)
        }
    }

}
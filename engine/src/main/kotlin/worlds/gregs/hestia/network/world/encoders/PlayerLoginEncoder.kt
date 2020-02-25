package worlds.gregs.hestia.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.PLAYER_LOGIN_LOBBY
import world.gregs.hestia.core.network.protocol.messages.PlayerLogin

class PlayerLoginEncoder : MessageEncoder<PlayerLogin>() {

    override fun encode(builder: PacketBuilder, message: PlayerLogin) {
        val (username, entity, client) = message
        builder.apply {
            writeOpcode(PLAYER_LOGIN_LOBBY, Packet.Type.VAR_BYTE)
            writeString(username)
            writeInt(entity)
            writeShort(client)
        }
    }

}
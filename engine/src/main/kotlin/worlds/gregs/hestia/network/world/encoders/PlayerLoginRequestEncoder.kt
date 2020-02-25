package worlds.gregs.hestia.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.PLAYER_LOGIN_GAME
import worlds.gregs.hestia.network.world.encoders.messages.PlayerLoginRequest

class PlayerLoginRequestEncoder : MessageEncoder<PlayerLoginRequest>() {

    override fun encode(builder: PacketBuilder, message: PlayerLoginRequest) {
        val (id, packet) = message
        builder.apply {
            writeOpcode(PLAYER_LOGIN_GAME, Packet.Type.VAR_SHORT)
            writeShort(id)
            writeBytes(packet.buffer)
        }
    }

}
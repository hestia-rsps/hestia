package worlds.gregs.hestia.network.game.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOGOUT
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOGOUT_LOBBY
import worlds.gregs.hestia.network.game.encoders.messages.Logout

class LogoutEncoder : MessageEncoder<Logout>() {

    override fun encode(builder: PacketBuilder, message: Logout) {
        val (lobby) = message
        builder.apply {
            writeOpcode(if (lobby) LOGOUT_LOBBY else LOGOUT)
        }
    }

}
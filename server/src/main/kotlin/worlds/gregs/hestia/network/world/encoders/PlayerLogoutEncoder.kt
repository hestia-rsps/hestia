package worlds.gregs.hestia.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.PLAYER_LOGOUT
import world.gregs.hestia.core.network.protocol.messages.PlayerLogout

class PlayerLogoutEncoder : MessageEncoder<PlayerLogout>() {

    override fun encode(builder: PacketBuilder, message: PlayerLogout) {
        val (entity) = message
        builder.apply {
            writeOpcode(PLAYER_LOGOUT)
            writeInt(entity)
        }
    }

}
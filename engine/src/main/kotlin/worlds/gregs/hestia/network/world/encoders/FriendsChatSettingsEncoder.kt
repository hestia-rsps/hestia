package worlds.gregs.hestia.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.FRIENDS_CHAT_SETTING
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSettings

class FriendsChatSettingsEncoder : MessageEncoder<FriendsChatSettings>() {

    override fun encode(builder: PacketBuilder, message: FriendsChatSettings) {
        val (entity, hash, option) = message
        builder.apply {
            writeOpcode(FRIENDS_CHAT_SETTING)
            writeInt(entity)
            writeInt(hash)
            writeByte(option)
        }
    }

}
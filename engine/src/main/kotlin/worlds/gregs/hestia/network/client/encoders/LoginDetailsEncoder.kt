package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOGIN_DETAILS
import worlds.gregs.hestia.network.client.encoders.messages.LoginDetails

class LoginDetailsEncoder : MessageEncoder<LoginDetails>() {

    override fun encode(builder: PacketBuilder, message: LoginDetails) {
        val (index, name, rights, isMember, membersWorld) = message
        builder.apply {
            writeOpcode(LOGIN_DETAILS, Packet.Type.VAR_BYTE)
            writeByte(rights)//Rights
            writeByte(0)//Unknown - something to do with skipping chat messages
            writeByte(0)
            writeByte(0)
            writeByte(0)
            writeByte(0)//Moves chat box position
            writeShort(index)//Player index
            writeByte(isMember)
            writeMedium(0)
            writeByte(membersWorld)
            writeString(name)//Display name
        }
    }

}
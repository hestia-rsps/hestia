package worlds.gregs.hestia.network.world.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.WorldOpcodes.WORLD_CONNECTION
import world.gregs.hestia.core.network.protocol.messages.WorldInfo

class WorldInfoEncoder : MessageEncoder<WorldInfo>() {

    override fun encode(builder: PacketBuilder, message: WorldInfo) {
        val (info) = message
        builder.apply {
            writeOpcode(WORLD_CONNECTION, Packet.Type.VAR_BYTE)
            writeByte(info.id)
            writeString(info.ip)
            writeString(info.region)
            writeByte(info.country)
            writeByte(info.location)
            writeString(info.activity)
            writeShort(info.flag)
        }
    }

}
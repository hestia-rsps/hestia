package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.io.Endian
import world.gregs.hestia.io.Modifier
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_COMPONENT_SETTINGS
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSettings

class InterfaceSettingsEncoder : MessageEncoder<InterfaceSettings>() {

    override fun encode(builder: PacketBuilder, message: InterfaceSettings) {
        val (id, component, fromSlot, toSlot, settings) = message
        builder.apply {
            writeOpcode(INTERFACE_COMPONENT_SETTINGS)
            writeShort(fromSlot, order = Endian.LITTLE)
            writeInt(id shl 16 or component, Modifier.INVERSE, Endian.MIDDLE)
            writeShort(toSlot, Modifier.ADD)
            writeInt(settings, order = Endian.LITTLE)
        }
    }

}
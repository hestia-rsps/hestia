package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_COMPONENT_SETTINGS
import worlds.gregs.hestia.network.client.encoders.messages.WidgetSettings

class WidgetSettingsEncoder : MessageEncoder<WidgetSettings>() {

    override fun encode(builder: PacketBuilder, message: WidgetSettings) {
        val (id, widget, fromSlot, toSlot, settings) = message
        builder.apply {
            writeOpcode(WIDGET_COMPONENT_SETTINGS)
            writeShort(fromSlot, order = Endian.LITTLE)
            writeInt(id shl 16 or widget, Modifier.INVERSE, Endian.MIDDLE)
            writeShort(toSlot, Modifier.ADD)
            writeInt(settings, order = Endian.LITTLE)
        }
    }

}
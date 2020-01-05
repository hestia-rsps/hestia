package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WIDGET_MOB_HEAD
import worlds.gregs.hestia.network.client.encoders.messages.WidgetHeadMob

class WidgetHeadMobEncoder : MessageEncoder<WidgetHeadMob>() {

    override fun encode(builder: PacketBuilder, message: WidgetHeadMob) {
        val (id, widget, mob) = message
        builder.apply {
            writeOpcode(WIDGET_MOB_HEAD)
            writeInt(id shl 16 or widget)
            writeShort(mob, order = Endian.LITTLE)
        }
    }

}
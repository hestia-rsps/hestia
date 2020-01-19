package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.INTERFACE_SPRITE
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSprite

class InterfaceSpriteEncoder : MessageEncoder<InterfaceSprite>() {

    override fun encode(builder: PacketBuilder, message: InterfaceSprite) {
        val (id, component, sprite) = message
        builder.apply {
            writeOpcode(INTERFACE_SPRITE)
            writeShort(sprite, order = Endian.LITTLE)
            writeInt(id shl 16 or component)
        }
    }

}
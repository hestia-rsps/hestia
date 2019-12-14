package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.packet.Packet

data class ClientMessageOut(val opcode: Int, val packet: Packet) : Message
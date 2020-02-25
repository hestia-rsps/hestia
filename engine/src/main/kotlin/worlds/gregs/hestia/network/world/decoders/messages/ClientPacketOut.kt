package worlds.gregs.hestia.network.world.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.packet.Packet

/**
 * A packet to send directly to the client
 * @param entity The player's entity id
 * @param packet The packet to process
 */
data class ClientPacketOut(val entity: Int, val packet: Packet) : Message
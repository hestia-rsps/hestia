package worlds.gregs.hestia.network.update.codec

import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.game.update.UpdateBlock

/**
 * Encodes data in an [UpdateBlock] into a packet
 */
interface UpdateBlockEncoder<T : UpdateBlock> {

    /**
     * Encodes data into the packet
     * @param builder The packet to encode data into
     * @param block The block who's data to encode
     */
    fun encode(builder: PacketBuilder, block: T)

}
package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Clears a chunk of all floor items
 * @param xOffset
 */
data class ChunkClear(val xOffset: Int, val yOffset: Int, val plane: Int) : Message
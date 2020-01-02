package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends a chunk for the client to base local position updates off of
 * @param xOffset The
 */
data class ChunkSend(val xOffset: Int, val yOffset: Int, val plane: Int) : Message
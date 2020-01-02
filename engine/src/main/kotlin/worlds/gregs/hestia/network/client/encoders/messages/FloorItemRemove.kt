package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Removes an item off of the floor
 */
data class FloorItemRemove(val id: Int, val positionOffset: Int) : Message
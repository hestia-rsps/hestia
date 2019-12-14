package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Updates the amount of a floor item
 * @param positionOffset The local position to show the item at
 * @param type The item id
 * @param oldAmount The previous item quantity
 * @param newAmount The latest item quantity (to show stack sizes)
 */
data class FloorItemUpdate(val positionOffset: Int, val type: Int, val oldAmount: Int, val newAmount: Int) : Message
package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Shows a floor item owned by the client player
 * @param positionOffset The local position to show the item at
 * @param id The item id
 * @param amount The item quantity (to show stack sizes)
 */
data class FloorItemPrivate(val positionOffset: Int, val id: Int, val amount: Int) : Message
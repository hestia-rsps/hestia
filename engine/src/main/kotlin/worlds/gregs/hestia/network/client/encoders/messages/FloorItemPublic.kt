package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Shows a public item on the floor
 * @param owner The index of the player who dropped the item (-1 for none)
 * @param positionOffset The local position to show the item at
 * @param id The item id
 * @param amount The item quantity (to show stack sizes)
 */
data class FloorItemPublic(val owner: Int, val positionOffset: Int, val id: Int, val amount: Int) : Message
package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends an item to display on a widget component
 * @param id The id of the parent widget
 * @param component The index of the component
 * @param item The item id
 * @param amount The number of the item
 */
data class WidgetItem(val id: Int, val component: Int, val item: Int, val amount: Int) : Message
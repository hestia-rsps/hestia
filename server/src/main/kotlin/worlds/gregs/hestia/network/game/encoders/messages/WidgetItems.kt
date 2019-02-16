package worlds.gregs.hestia.network.game.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends a list of items to display on a widget item group
 * @param key The id of the widget item group
 * @param items List of the item id's to display
 * @param negativeKey Whether the key is negative and needs encoding differently (optional - calculated automatically)
 * TODO support for item amounts
 */
data class WidgetItems(val key: Int, val items: List<Int>, val negativeKey: Boolean = key < 0) : Message
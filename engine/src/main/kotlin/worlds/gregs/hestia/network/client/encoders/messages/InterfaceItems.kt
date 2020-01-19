package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends a list of items to display on a interface item group component
 * @param key The id of the interface item group
 * @param items List of the item ids and amounts to display
 * @param negativeKey Whether the key is negative and needs encoding differently (optional - calculated automatically)
 */
data class InterfaceItems(val key: Int, val items: List<Pair<Int, Int>>, val negativeKey: Boolean = key < 0) : Message
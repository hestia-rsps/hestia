package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Widget action applied to an object
 * @param first Unknown value
 * @param y The objects y coordinate
 * @param slot The widget item slot
 * @param hash The widget & component id
 * @param item The item id
 * @param y The objects y coordinate
 * @param id The objects id
 */
data class WidgetOnObject(val first: Boolean, val y: Int, val slot: Int, val hash: Int, val item: Int, val x: Int, val id: Int) : Message
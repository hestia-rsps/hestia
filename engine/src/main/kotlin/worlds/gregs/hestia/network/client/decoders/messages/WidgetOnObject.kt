package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Widget action applied to an object
 * @param run Force run
 * @param y The objects y coordinate
 * @param slot The widget item slot
 * @param hash The widget & component id
 * @param type The item id
 * @param y The objects y coordinate
 * @param id The objects id
 */
data class WidgetOnObject(val run: Boolean, val y: Int, val slot: Int, val hash: Int, val type: Int, val x: Int, val id: Int) : Message
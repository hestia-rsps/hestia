package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Widget action applied to a floor item
 * @param x The floor x coordinate
 * @param y The floor x coordinate
 * @param floorType The item type of the floor item
 * @param hash The widget id & spell id hash
 * @param slot The widget slot
 * @param run Force run
 * @param item The item type of the widget item
 */
data class WidgetOnFloorItem(val x: Int, val y: Int, val floorType: Int, val hash: Int, val slot: Int, val run: Boolean, val item: Int) : Message
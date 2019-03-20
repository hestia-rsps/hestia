package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Widget action applied to a floor item
 * @param x The floor x coordinate
 * @param y The floor x coordinate
 * @param first Unknown value
 * @param hash The widget id & spell id hash
 * @param second Unknown value
 * @param third Unknown value
 * @param item The item id
 */
data class WidgetOnFloorItem(val x: Int, val y: Int, val first: Int, val hash: Int, val second: Int, val third: Boolean, val item: Int) : Message
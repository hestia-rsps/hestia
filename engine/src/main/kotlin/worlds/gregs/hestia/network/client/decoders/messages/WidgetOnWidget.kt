package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Widget action applied to another widget
 * @param fromHash The first window and widget id combined
 * @param fromItem Item id of the first slot
 * @param from The slot being used
 * @param toHash The second window and widget id combined
 * @param toItem Item id of the second slot
 * @param to The slot being applied too
 */
data class WidgetOnWidget(val fromHash: Int, val fromItem: Int, val from: Int, val toHash: Int, val toItem: Int, val to: Int) : Message
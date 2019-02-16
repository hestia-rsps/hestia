package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Action to switch two widget components
 * @param first Unknown value
 * @param from The slot being dragged
 * @param second Unknown value
 * @param fromHash The first widget & component ids hash
 * @param to The slot dropped on
 * @param toHash The second widget & component ids hash
 */
data class WidgetSwitchComponent(val first: Int, val from: Int, val second: Int, val fromHash: Int, val to: Int, val toHash: Int) : Message

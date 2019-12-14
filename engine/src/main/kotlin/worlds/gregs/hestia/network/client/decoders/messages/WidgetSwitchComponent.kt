package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Action to switch two widget components
 * @param fromType The first item type
 * @param fromSlot The first item slot
 * @param toType The second item type
 * @param fromHash The first widget & component ids hash
 * @param toSlot The second item slot
 * @param toHash The second widget & component ids hash
 */
data class WidgetSwitchComponent(val fromType: Int, val fromSlot: Int, val toType: Int, val fromHash: Int, val toSlot: Int, val toHash: Int) : Message

package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Action of one component dragged to another
 * @param fromType The first item type
 * @param fromSlot The first item slot
 * @param toType The second item type
 * @param fromHash The first interface and component ids hash
 * @param toSlot The second item slot
 * @param toHash The second interface and component ids hash
 */
data class InterfaceSwitchComponents(val fromType: Int, val fromSlot: Int, val toType: Int, val fromHash: Int, val toSlot: Int, val toHash: Int) : Message

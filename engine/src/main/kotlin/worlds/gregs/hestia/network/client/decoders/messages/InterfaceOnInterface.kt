package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Interface container action applied to another interface container
 * @param fromHash The first interface and component id combined
 * @param fromItem Item id of the first slot
 * @param from The slot being used
 * @param toHash The second interface and component id combined
 * @param toItem Item id of the second slot
 * @param to The slot being applied too
 */
data class InterfaceOnInterface(val fromHash: Int, val fromItem: Int, val from: Int, val toHash: Int, val toItem: Int, val to: Int) : Message
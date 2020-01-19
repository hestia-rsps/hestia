package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Interface container action applied to a player
 * @param player The player index to apply on
 * @param hash The interface and component id
 * @param type The interface item type
 * @param run Force run
 * @param slot The component item slot
 */
data class InterfaceOnPlayer(val player: Int, val hash: Int, val type: Int, val run: Boolean, val slot: Int) : Message
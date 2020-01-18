package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Interface container action applied to a mob
 * @param slot The interface item slot
 * @param type The interface item type
 * @param mob The mobs client index
 * @param hash The interface and component id
 * @param run Force run
 */
data class InterfaceOnMob(val slot: Int, val type: Int, val mob: Int, val hash: Int, val run: Boolean) : Message

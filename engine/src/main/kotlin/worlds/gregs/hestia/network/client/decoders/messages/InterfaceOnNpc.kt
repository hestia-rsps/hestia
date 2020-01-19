package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Interface container action applied to a npc
 * @param slot The interface item slot
 * @param type The interface item type
 * @param npc The npc client index
 * @param hash The interface and component id
 * @param run Force run
 */
data class InterfaceOnNpc(val slot: Int, val type: Int, val npc: Int, val hash: Int, val run: Boolean) : Message

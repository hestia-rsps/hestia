package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Requests a change to the players clans name
 * @param name The new clan name
 */
data class ClanName(val name: String) : Message
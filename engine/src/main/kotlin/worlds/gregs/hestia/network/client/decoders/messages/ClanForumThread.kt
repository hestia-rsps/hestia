package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Requests a change to the players clans forum thread
 * @param string The clans forum thread
 */
data class ClanForumThread(val string: String) : Message
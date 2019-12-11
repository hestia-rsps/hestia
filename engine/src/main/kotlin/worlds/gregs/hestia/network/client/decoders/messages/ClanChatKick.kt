package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Attempt to kick a clan mate
 * @param owner Whether is the players clan - aka myClan
 * @param equals Whether the name is a match
 * @param member The display name of the member to kick
 */
data class ClanChatKick(val owner: Boolean, val equals: Int, val member: String) : Message
package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends the player details to the client
 * @param index The player client index
 * @param name The players name
 * @param rights The players rights (0 = normal, 1 = player mod, 2 = admin)
 * @param isMember Whether player is a member
 * @param membersWorld Whether client shouldn't display members items
 */
data class LoginDetails(val index: Int, val name: String, val rights: Int, val isMember: Boolean, val membersWorld: Boolean) : Message
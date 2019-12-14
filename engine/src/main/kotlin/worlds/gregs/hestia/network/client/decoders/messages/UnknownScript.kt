package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * String from script 4701, game state must not be 7 and length must be less than or equal to 20
 * Might be kicking or banning from clan chat via interface
 * @param string Unknown value
 */
data class UnknownScript(val string: String) : Message
package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * An integer entered in the entry box dialogue
 * @param integer The value entered
 */
data class IntegerEntry(val integer: Int) : Message
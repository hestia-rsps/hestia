package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Information entered in a enter string dialogue pop-up
 * @param text The string entered
 */
data class StringEntry(val text: String) : Message
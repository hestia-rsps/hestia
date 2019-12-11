package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * @param value Some kind of colour value
 */
data class Unknown(val value: Int) : Message
package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * A variable bit of a [Varp]; also known as "ConfigFile", known in the client as "clientvarpbit
 * @param id The file id
 * @param value The value to pass to the config file
 * @param large Whether to encode value with integer rather than short (optional - calculated automatically)
 */
data class Varbit(val id: Int, val value: Int, val large: Boolean = value !in Byte.MIN_VALUE..Byte.MAX_VALUE) : Message
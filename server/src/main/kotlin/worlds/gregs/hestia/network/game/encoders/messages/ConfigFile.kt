package worlds.gregs.hestia.network.game.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Calls a client config by exact file id
 * @param id The file id
 * @param value The value to pass to the config file
 * @param large Whether to encode value with integer rather than short (optional - calculated automatically)
 */
data class ConfigFile(val id: Int, val value: Int, val large: Boolean = value !in Byte.MIN_VALUE..Byte.MAX_VALUE) : Message
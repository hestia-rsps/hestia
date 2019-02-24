package worlds.gregs.hestia.network.game.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
/**
 * Calls a global client config
 * @param id The config id
 * @param value The value to pass to the config
 * @param large Whether to encode value with integer rather than short (optional - calculated automatically)
 */
data class ConfigGlobal(val id: Int, val value: Int, val large: Boolean = value !in Byte.MIN_VALUE..Byte.MAX_VALUE) : Message
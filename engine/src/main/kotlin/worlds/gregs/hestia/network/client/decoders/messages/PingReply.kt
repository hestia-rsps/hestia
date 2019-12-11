package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * The two values sent the client by packet 19
 */
data class PingReply(val first: Int, val second: Int) : Message
package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Called when the client window changes status
 * @param focused Whether the client is focused or not
 */
data class WindowFocus(val focused: Boolean) : Message
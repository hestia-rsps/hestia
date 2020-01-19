package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Called when the users mouse enters or exits the client area
 * @param over Whether the mouse is over the client or not
 */
data class WindowHovered(val over: Boolean) : Message
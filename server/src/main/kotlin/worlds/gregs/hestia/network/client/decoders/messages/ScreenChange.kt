package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Notification that the player has changed their screen mode and might need a widget refresh
 * @param displayMode The client display mode
 * @param width The client window width
 * @param height The client window height
 * @param antialiasLevel The client antialias level
 */
data class ScreenChange(val displayMode: Int, val width: Int, val height: Int, val antialiasLevel: Int) : Message
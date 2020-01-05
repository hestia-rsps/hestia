package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends an animation to a window widget
 * @param id The id of the parent window
 * @param widget The index of the widget
 * @param animation The animation id
 */
data class WindowWidgetAnimation(val id: Int, val widget: Int, val animation: Int) : Message
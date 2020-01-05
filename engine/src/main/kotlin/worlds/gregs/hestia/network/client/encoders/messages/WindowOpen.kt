package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Displays a window onto the client
 * @param permanent Whether the widget is permanent (frame) or temporary (screen)
 * @param window The id of the parent window
 * @param widget The index of the widget
 * @param id The id of the widget to display
 */
data class WindowOpen(val permanent: Boolean, val window: Int, val widget: Int, val id: Int) : Message
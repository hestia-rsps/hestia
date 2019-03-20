package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Displays a widget onto the client
 * @param permanent Whether the widget is permanent (frame) or temporary (screen)
 * @param window The id of the parent widget
 * @param component The index of the component
 * @param id The id of the widget to display
 */
data class WidgetOpen(val permanent: Boolean, val window: Int, val component: Int, val id: Int) : Message
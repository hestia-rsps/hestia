package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends command to display the players head on a window widget
 * @param id The id of the parent window
 * @param widget The index of the widget
 */
data class WidgetHeadPlayer(val id: Int, val widget: Int) : Message
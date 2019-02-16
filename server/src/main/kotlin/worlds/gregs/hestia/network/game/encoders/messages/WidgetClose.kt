package worlds.gregs.hestia.network.game.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Closes a client widget
 * @param id The id of the parent widget
 * @param component The index of the widget to close
 */
data class WidgetClose(val id: Int, val component: Int) : Message
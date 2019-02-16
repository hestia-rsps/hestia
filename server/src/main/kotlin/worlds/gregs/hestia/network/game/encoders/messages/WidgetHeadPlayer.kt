package worlds.gregs.hestia.network.game.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends command to display the players head on a widget component
 * @param id The id of the parent widget
 * @param component The index of the component
 */
data class WidgetHeadPlayer(val id: Int, val component: Int) : Message
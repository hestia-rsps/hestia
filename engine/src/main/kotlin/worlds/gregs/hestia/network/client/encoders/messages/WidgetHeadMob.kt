package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends mob who's head to display on a window widget
 * @param id The id of the parent window
 * @param widget The index of the widget
 * @param mob The id of the mob
 */
data class WidgetHeadMob(val id: Int, val widget: Int, val mob: Int) : Message
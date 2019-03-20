package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends mob who's head to display on a widget component
 * @param id The id of the parent widget
 * @param component The index of the component
 * @param mob The id of the mob
 */
data class WidgetHeadMob(val id: Int, val component: Int, val mob: Int) : Message
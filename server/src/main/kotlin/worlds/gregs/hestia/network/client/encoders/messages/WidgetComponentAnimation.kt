package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends an animation to a widget component
 * @param id The id of the parent widget
 * @param component The index of the component
 * @param animation The animation id
 */
data class WidgetComponentAnimation(val id: Int, val component: Int, val animation: Int) : Message
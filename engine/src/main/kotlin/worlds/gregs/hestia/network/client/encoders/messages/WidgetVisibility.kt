package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Toggles a window widget
 * @param id The parent window id
 * @param widget The widget to change
 * @param hide Visibility
 */
data class WidgetVisibility(val id: Int, val widget: Int, val hide: Boolean) : Message
package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Updates an area of the client display
 * @param id The id of the window/widget to change
 * @param type The type of change to make (0 = open, 2 = close)
 */
data class WidgetWindowsPane(val id: Int, val type: Int) : Message
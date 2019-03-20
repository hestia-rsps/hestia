package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Widget action applied to a player
 * @param player The player index to apply on
 * @param hash The widget & component id
 * @param first Unknown value
 * @param second Unknown value
 * @param third Unknown value
 */
data class WidgetOnPlayer(val player: Int, val hash: Int, val first: Int, val second: Boolean, val third: Int) : Message
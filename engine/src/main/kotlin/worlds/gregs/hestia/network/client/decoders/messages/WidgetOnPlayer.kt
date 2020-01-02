package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Widget action applied to a player
 * @param player The player index to apply on
 * @param hash The widget & component id
 * @param type The widget item type
 * @param run Force run
 * @param slot The widget item slot
 */
data class WidgetOnPlayer(val player: Int, val hash: Int, val type: Int, val run: Boolean, val slot: Int) : Message
package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Widget action applied to a player
 * @param slot The widget item slot
 * @param type The widget item type
 * @param mob The mobs client index
 * @param hash The widget & component id
 * @param run Force run
 */
data class WidgetOnMob(val slot: Int, val type: Int, val mob: Int, val hash: Int, val run: Boolean) : Message

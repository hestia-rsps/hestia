package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Widget action applied to a player
 * @param slot The widget item slot
 * @param first Unknown value
 * @param mob The mobs client index
 * @param hash The widget & component id
 * @param second Unknown value
 */
data class WidgetOnMob(val slot: Int, val first: Int, val mob: Int, val hash: Int, val second: Boolean) : Message

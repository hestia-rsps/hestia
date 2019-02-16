package worlds.gregs.hestia.network.game.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * When a widget button is clicked directly or using a right click option choice
 * @param hash The widget id and component id combined
 * @param fromSlot Optional starting slot index
 * @param toSlot Optioning finishing slot index
 * @param option The menu option index
 */
data class WidgetClick(val hash: Int, val fromSlot: Int, val toSlot: Int, val option: Int) : Message
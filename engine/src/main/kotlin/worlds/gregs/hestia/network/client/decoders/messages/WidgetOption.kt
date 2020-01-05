package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * When a widget button is clicked directly or using a right click option choice
 * @param hash The window and widget id combined
 * @param paramOne Optional starting slot index
 * @param paramTwo Optioning finishing slot index
 * @param option The menu option index
 */
data class WidgetOption(val hash: Int, val paramOne: Int, val paramTwo: Int, val option: Int) : Message
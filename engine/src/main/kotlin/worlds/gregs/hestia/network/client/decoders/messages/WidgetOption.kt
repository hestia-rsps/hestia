package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * When a widget button is clicked directly or using a right click option choice
 * @param hash The widget id and component id combined
 * @param from Optional starting slot index
 * @param to Optioning finishing slot index
 * @param option The menu option index
 */
data class WidgetOption(val hash: Int, val from: Int, val to: Int, val option: Int) : Message
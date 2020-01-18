package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * When a interface button is clicked directly or using a right click option choice
 * @param hash The interface and component id combined
 * @param paramOne Optional starting slot index
 * @param paramTwo Optioning finishing slot index
 * @param option The menu option index
 */
data class InterfaceOption(val hash: Int, val paramOne: Int, val paramTwo: Int, val option: Int) : Message
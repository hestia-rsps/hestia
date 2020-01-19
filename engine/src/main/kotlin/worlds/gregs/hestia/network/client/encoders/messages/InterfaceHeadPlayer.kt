package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends command to display the players head on a interface component
 * @param id The id of the parent interface
 * @param component The index of the component
 */
data class InterfaceHeadPlayer(val id: Int, val component: Int) : Message
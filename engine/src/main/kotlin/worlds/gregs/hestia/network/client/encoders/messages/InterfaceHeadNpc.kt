package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends npc who's head to display on a interface component
 * @param id The id of the parent interface
 * @param component The index of the component
 * @param npc The id of the npc
 */
data class InterfaceHeadNpc(val id: Int, val component: Int, val npc: Int) : Message
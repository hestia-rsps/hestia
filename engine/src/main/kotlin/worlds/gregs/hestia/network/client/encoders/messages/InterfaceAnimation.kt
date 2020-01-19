package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends an animation to a interface component
 * @param id The id of the parent interface
 * @param component The index of the component
 * @param animation The animation id
 */
data class InterfaceAnimation(val id: Int, val component: Int, val animation: Int) : Message
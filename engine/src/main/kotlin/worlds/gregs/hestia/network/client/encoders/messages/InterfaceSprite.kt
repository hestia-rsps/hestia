package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends a sprite to a interface component
 * @param id The id of the parent interface
 * @param component The index of the component
 * @param sprite The sprite id
 */
data class InterfaceSprite(val id: Int, val component: Int, val sprite: Int) : Message
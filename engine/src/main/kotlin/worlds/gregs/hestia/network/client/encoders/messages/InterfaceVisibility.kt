package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Toggles a interface component
 * @param id The parent interface id
 * @param component The component to change
 * @param hide Visibility
 */
data class InterfaceVisibility(val id: Int, val component: Int, val hide: Boolean) : Message
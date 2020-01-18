package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends settings to a interface's component(s)
 * @param id The id of the parent window
 * @param component The index of the component
 * @param fromSlot The start slot index
 * @param toSlot The end slot index
 * @param settings The settings hash
 */
data class InterfaceSettings(val id: Int, val component: Int, val fromSlot: Int, val toSlot: Int, val settings: Int) : Message {

    constructor(id: Int, component: Int, fromSlot: Int, toSlot: Int, vararg options: Int) : this(id, component, fromSlot, toSlot, settings(*options))

    companion object {
        /**
         * Combines a list of options into a single setting integer
         * @param options The options to combine
         * @return The settings id
         */
        private fun settings(vararg options: Int): Int {
            var settings = 0
            for(slot in options) {
                settings = settings or (2 shl slot)
            }
            return settings
        }
    }
}
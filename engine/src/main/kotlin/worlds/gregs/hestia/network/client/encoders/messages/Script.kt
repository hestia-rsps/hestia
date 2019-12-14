package worlds.gregs.hestia.network.client.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends a client script to run
 * @param id The client script id
 * @param params Additional parameters to run the script with (strings & integers only)
 */
data class Script(val id: Int, val params: List<Any>): Message {
    constructor(id: Int, vararg params: Any) : this(id, params.toList())
}
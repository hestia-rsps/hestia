package worlds.gregs.hestia.network.client

import world.gregs.hestia.core.network.codec.message.Message

interface ClientMessageHandler<T : Message> {

    /**
     * Handles data from a message, potentially adds a new message to the pipeline
     * @param entityId The entity the message is from
     * @param message The message
     */
    fun handle(entityId: Int, message: T)

}
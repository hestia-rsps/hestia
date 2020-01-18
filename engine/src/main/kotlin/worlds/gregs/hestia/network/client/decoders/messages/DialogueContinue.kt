package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Notification that the "Click here to continue" button was pressed on a dialogue
 * @param hash The interface and component id combined
 * @param index The index of the interface select
 */
data class DialogueContinue(val hash: Int, val index: Int) : Message
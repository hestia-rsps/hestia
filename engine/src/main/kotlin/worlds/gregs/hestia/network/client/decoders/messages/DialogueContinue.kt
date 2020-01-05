package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Notification that the "Click here to continue" button was pressed on a dialogue
 * @param hash The window and widget id combined
 * @param widget The index of the widget select
 */
data class DialogueContinue(val hash: Int, val widget: Int) : Message
package worlds.gregs.hestia.network.game.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Notification that the "Click here to continue" button was pressed on a dialogue
 * @param hash The widget id and button id combined
 * @param component The index of the component
 */
data class DialogueContinue(val hash: Int, val component: Int) : Message
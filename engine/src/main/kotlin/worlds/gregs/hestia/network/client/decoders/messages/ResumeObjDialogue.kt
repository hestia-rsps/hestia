package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Called by script 580 - G.E item search clearing
 * @param value Unknown value
 */
data class ResumeObjDialogue(val value: Int) : Message
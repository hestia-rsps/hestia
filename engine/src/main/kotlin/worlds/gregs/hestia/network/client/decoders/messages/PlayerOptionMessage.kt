package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * An option selection on another player
 * @param index The selected player's index
 * @param option The option id - 3 = Trade, 4 = Attack
 */
data class PlayerOptionMessage(val index: Int, val option: Int) : Message
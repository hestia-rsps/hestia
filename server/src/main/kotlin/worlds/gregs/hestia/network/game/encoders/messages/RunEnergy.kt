package worlds.gregs.hestia.network.game.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Sends run energy
 * @param energy The current energy value
 */
data class RunEnergy(val energy: Int) : Message
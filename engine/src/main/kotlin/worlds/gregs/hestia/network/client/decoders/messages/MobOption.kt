package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * An option selection on a mob
 * @param run Whether the player should force run
 * @param mobIndex The mobs client index
 * @param option The option id - 2 = Attack, 6 = Examine
 */
data class MobOption(val run: Boolean, val mobIndex: Int, val option: Int) : Message
package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * An option selection on a npc
 * @param run Whether the player should force run
 * @param npcIndex The npc client index
 * @param option The option id - 2 = Attack, 6 = Examine
 */
data class NpcOptionMessage(val run: Boolean, val npcIndex: Int, val option: Int) : Message
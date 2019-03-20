package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Teleport request send when attempted using action 11 (unknown) and isn't a mod
 */
data class SecondaryTeleport(val x: Int, val y: Int) : Message
package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Request for player player to move from current position to a new position via mini-map
 * @param x The target tile x coordinate
 * @param y The target tile y coordinate
 * @param running Whether the client is displaying the player as running
 */
data class WalkMiniMap(val x: Int, val y: Int, val running: Boolean) : Message
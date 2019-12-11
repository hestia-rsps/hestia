package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Request for a player to move from current position to a new position on the map
 * @param x The target tile x coordinate
 * @param y The target tile y coordinate
 * @param running Whether the client is displaying the player as running
 */
data class WalkMap(val x: Int, val y: Int, val running: Boolean) : Message//TODO test running
package worlds.gregs.hestia.network.game.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * The route the client has calculated for the player to move from current position to a new position
 * @param x The starting tile x coordinate
 * @param y The starting tile y coordinate
 * @param route List of the delta steps required to reach the target
 * @param running Whether the client is displaying the player as running
 */
data class WalkTarget(val x: Int, val y: Int, val route: List<Pair<Int, Int>>, val running: Boolean) : Message//TODO test running
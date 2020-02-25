package worlds.gregs.hestia.network.world.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.packet.Packet

/**
 * Game login request to redirect to login/social-server
 * @param id The game-server's session id
 * @param packet The client's login request packet
 */
data class PlayerLoginRequest(val id: Int, val packet: Packet) : Message
package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.packet.Packet

/**
 * An attempt to log into the game from login-screen or lobby
 * @param packet The entire packet to be passed to the login/social server
 */
data class GameLogin(val packet: Packet) : Message
package worlds.gregs.hestia.network.game.encoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Disconnect client
 * @param lobby Whether to attempt reconnect to lobby
 */
data class Logout(val lobby: Boolean) : Message
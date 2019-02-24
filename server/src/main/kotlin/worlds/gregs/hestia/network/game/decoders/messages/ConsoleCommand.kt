package worlds.gregs.hestia.network.game.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * A command typed out in the client console
 * @param command The command sent by the player
 */
data class ConsoleCommand(val command: String) : Message
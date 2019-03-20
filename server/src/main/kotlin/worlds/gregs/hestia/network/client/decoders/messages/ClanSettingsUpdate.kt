package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * @param first Unknown value
 * @param string Unknown value
 */
data class ClanSettingsUpdate(val first: Int, val string: String) : Message
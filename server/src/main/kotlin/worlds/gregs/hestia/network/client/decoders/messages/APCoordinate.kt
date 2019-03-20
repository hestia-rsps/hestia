package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

data class APCoordinate(val first: Int, val second: Int, val third: Int, val fourth: Int, val fifth: Int) : Message
package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

data class WindowFocus(val focused: Boolean) : Message
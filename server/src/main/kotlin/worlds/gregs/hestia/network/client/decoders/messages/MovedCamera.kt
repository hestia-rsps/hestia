package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

data class MovedCamera(val pitch: Int, val yaw: Int) : Message
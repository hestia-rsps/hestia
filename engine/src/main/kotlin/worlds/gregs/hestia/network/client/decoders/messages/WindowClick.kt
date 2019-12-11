package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * A click on the game window
 * @param hash Hash of last time since last click (max 32767) & right click boolean (time | rightClick << 15)
 * @param position Position hash (x | y << 16)
 */
data class WindowClick(val hash: Int, val position: Int) : Message
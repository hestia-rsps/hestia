package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * @param keys key's pressed - Pair<Key, Time>
 */
data class KeysPressed(val keys: List<Pair<Int, Int>>) : Message
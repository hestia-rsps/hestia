package worlds.gregs.hestia.network.client.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * An option selection on an object
 * @param objectId The type id of the object selected
 * @param x The object's x coordinate
 * @param y The object's y coordinate
 * @param run Whether the player should force run
 * @param option The option id - 6 = Examine
 */
data class ObjectOption(val objectId: Int, val x: Int, val y: Int, val run: Boolean, val option: Int) : Message
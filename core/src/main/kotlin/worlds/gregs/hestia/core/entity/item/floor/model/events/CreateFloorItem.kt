package worlds.gregs.hestia.core.entity.item.floor.model.events

import worlds.gregs.hestia.artemis.InstantEvent

/**
 * Creates a floor item entity
 * @param type The type of item
 * @param amount The quantity of item
 * @param x The x position
 * @param y The y position
 * @param plane The plane
 */
data class CreateFloorItem(val type: Int, val amount: Int, val x: Int, val y: Int, val plane: Int, val owner: String?, val privateTime: Int?, val clientIndex: Int?, val publicTime: Int?) : InstantEvent {
    constructor(id: Int, amount: Int, x: Int, y: Int, plane: Int, owner: String, privateTime: Int) : this(id, amount, x, y, plane, owner, privateTime, null, null)

    constructor(id: Int, amount: Int, x: Int, y: Int, plane: Int, clientIndex: Int?, publicTime: Int) : this(id, amount, x, y, plane, null, null, clientIndex, publicTime)

}
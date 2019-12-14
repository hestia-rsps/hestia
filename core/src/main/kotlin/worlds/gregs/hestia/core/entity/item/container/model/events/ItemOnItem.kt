package worlds.gregs.hestia.core.entity.item.container.model.events

import worlds.gregs.hestia.core.task.api.event.EntityEvent

/**
 * Using an item on another item
 * @param entity The entity using the item
 * @param fromHash The first container interface hash
 * @param fromType The first item type
 * @param fromIndex The first item slot
 * @param toHash The second container interface hash
 * @param toType The second item type
 * @param toIndex The second item slot
 */
data class ItemOnItem(override val entity: Int, val fromHash: Int, val fromType: Int, val fromIndex: Int, val toHash: Int, val toType: Int, val toIndex: Int) : EntityEvent
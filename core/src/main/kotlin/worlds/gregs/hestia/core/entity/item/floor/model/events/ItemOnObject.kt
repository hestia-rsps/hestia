package worlds.gregs.hestia.core.entity.item.floor.model.events

import worlds.gregs.hestia.core.task.api.event.TargetEvent

/**
 * Using an item on a game object
 * @param entity The entity using the item
 * @param target The object target
 * @param hash The container interface hash
 * @param slot The container slot
 * @param type The item type
 */
data class ItemOnObject(override val entity: Int, override val target: Int, val hash: Int, val slot: Int, val type: Int) : TargetEvent
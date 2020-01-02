package worlds.gregs.hestia.core.entity.item.container.model.events

import worlds.gregs.hestia.core.task.api.event.TargetEvent

/**
 * Using an item on a mob
 * @param entity The entity using the item
 * @param target The mob target
 * @param slot The container slot
 * @param type The item type
 */
data class ItemOnMob(override val entity: Int, override val target: Int, val slot: Int, val type: Int) : TargetEvent
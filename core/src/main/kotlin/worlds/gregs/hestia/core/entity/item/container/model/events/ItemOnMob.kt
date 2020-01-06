package worlds.gregs.hestia.core.entity.item.container.model.events

import worlds.gregs.hestia.core.action.Action

/**
 * Using an item on a mob
 * @param target The mob target
 * @param slot The container slot
 * @param type The item type
 */
data class ItemOnMob(val target: Int, val slot: Int, val type: Int) : Action()
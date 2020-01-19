package worlds.gregs.hestia.core.entity.item.container.model.events

import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * Using an item on a npc
 * @param npc The npc target
 * @param slot The container slot
 * @param type The item type
 */
data class ItemOnNpc(val npc: Int, val slot: Int, val type: Int) : EntityAction()
package worlds.gregs.hestia.core.entity.item.floor.model.events

import worlds.gregs.hestia.core.action.model.EntityAction

/**
 * Using an item on a game object
 * @param target The object target
 * @param hash The container interface hash
 * @param slot The container slot
 * @param type The item type
 */
data class ItemOnObject(val target: Int, val hash: Int, val slot: Int, val type: Int) : EntityAction()
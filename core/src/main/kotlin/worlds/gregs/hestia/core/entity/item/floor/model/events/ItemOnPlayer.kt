package worlds.gregs.hestia.core.entity.item.floor.model.events

import worlds.gregs.hestia.core.action.Action

/**
 * Using an item on a game object
 * @param target The target player entity id
 * @param hash The container interface hash
 * @param slot The container slot
 * @param type The item type
 */
data class ItemOnPlayer(val target: Int, val hash: Int, val slot: Int, val type: Int) : Action()
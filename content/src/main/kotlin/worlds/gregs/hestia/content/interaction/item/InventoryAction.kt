package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.entity.item.container.model.Item

data class InventoryAction(val item: Item, val slot: Int, val option: String): EntityAction(), InstantEvent
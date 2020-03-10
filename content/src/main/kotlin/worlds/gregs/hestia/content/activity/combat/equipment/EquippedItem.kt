package worlds.gregs.hestia.content.activity.combat.equipment

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.entity.item.container.model.Item

data class EquippedItem(val item: Item) : EntityAction(), InstantEvent
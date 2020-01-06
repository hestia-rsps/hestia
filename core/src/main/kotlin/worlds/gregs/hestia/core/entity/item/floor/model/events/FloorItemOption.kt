package worlds.gregs.hestia.core.entity.item.floor.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.Action

/**
 * An [option] on [target] selected by [entity]
 * @param target The entity id of the floor item
 * @param option The option selected
 */
data class FloorItemOption(val target: Int, val option: String): Action(), InstantEvent
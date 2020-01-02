package worlds.gregs.hestia.core.entity.item.floor.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.task.api.event.TargetEvent

/**
 * An [option] on [target] selected by [entity]
 * @param entity The entity requesting the action
 * @param target The entity id of the floor item
 * @param option The option selected
 */
data class FloorItemOption(override val entity: Int, override val target: Int, val option: String): TargetEvent, InstantEvent
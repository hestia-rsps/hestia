package worlds.gregs.hestia.core.entity.`object`.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.task.api.event.TargetEvent

/**
 * An [option] on [target] selected by [entity]
 * @param entity The entity requesting the action
 * @param target The entity id of the object
 * @param option The option selected
 */
data class ObjectOption(override val entity: Int, override val target: Int, val option: String, val name: String) : TargetEvent, InstantEvent
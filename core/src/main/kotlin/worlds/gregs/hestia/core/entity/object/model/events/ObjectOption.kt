package worlds.gregs.hestia.core.entity.`object`.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.Action

/**
 * An [option] on [target] selected by [entity]
 * @param target The entity id of the object
 * @param option The option selected
 */
data class ObjectOption(val target: Int, val option: String, val name: String) : Action(), InstantEvent
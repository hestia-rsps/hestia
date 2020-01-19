package worlds.gregs.hestia.core.display.variable.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class RemoveVariable<T : Any>(val key: String, val value: T, val refresh: Boolean = true) : EntityAction(), InstantEvent
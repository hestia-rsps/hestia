package worlds.gregs.hestia.core.display.interfaces.model.events.variable

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class SetVariable<T : Any>(val key: String, val value: T, val refresh: Boolean = true) : EntityAction(), InstantEvent
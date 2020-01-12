package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class RemoveVariable<T>(val key: String, val value: T) : EntityAction(), InstantEvent
package worlds.gregs.hestia.core.display.window.model.events.variable

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class SendVariable(val key: String) : EntityAction(), InstantEvent
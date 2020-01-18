package worlds.gregs.hestia.core.display.interfaces.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class InterfaceRefresh(val id: Int) : EntityAction(), InstantEvent
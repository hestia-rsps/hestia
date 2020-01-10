package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class WindowRefresh(val target: Int) : EntityAction(), InstantEvent
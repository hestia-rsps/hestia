package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.Action

data class WindowRefresh(val target: Int) : Action(), InstantEvent
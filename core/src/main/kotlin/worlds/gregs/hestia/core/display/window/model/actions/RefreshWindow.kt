package worlds.gregs.hestia.core.display.window.model.actions

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class RefreshWindow(val target: Int): EntityAction(), InstantEvent
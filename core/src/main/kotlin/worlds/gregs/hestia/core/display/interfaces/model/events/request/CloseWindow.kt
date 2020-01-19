package worlds.gregs.hestia.core.display.interfaces.model.events.request

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.interfaces.model.Window

data class CloseWindow(val window: Window): EntityAction(), InstantEvent
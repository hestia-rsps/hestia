package worlds.gregs.hestia.core.display.window.model.actions

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.window.model.WindowPane

data class CloseWindowPane(val pane: WindowPane): EntityAction(), InstantEvent
package worlds.gregs.hestia.core.display.window.model.actions

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.Action
import worlds.gregs.hestia.core.display.window.model.WindowPane

data class CloseWindowPane(val pane: WindowPane): Action(), InstantEvent
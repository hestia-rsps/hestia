package worlds.gregs.hestia.content.display.fullscreen

import worlds.gregs.hestia.content.display.Tabs.tabNames
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ResizableGameframe
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable

val gameframes = intArrayOf(FixedGameframe, ResizableGameframe)

on(InterfaceOption, "Advisor", ids = *gameframes) { _, _, _, _ ->
}

on(InterfaceOption, "Face North", ids = *gameframes) { _, _, _, _ ->
}

on(InterfaceOption, "Toggle XP Total", ids = *gameframes) { _, _, _, _ ->
}

on(InterfaceOption, "Reset XP Total", ids = *gameframes) { _, _, _, _ ->
}

on(InterfaceOption, "Reset XP Total", ids = *gameframes) { _, _, _, _ ->
}

tabNames.forEach { tabName ->
    on(InterfaceOption, tabName, ids = *gameframes) { _, _, _, _ ->
        entity perform SetVariable("tab", tabName, false)
    }
}
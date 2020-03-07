package worlds.gregs.hestia.content.display.main

import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.getInterfaceComponentId
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FilterButtons

on(InterfaceOption, "View", id = FilterButtons) { hash, _, _, _ ->
    when(getInterfaceComponentId(hash)) {
        31 -> {//View
        }
        34 -> {//View Game
        }
        28 -> {//View Public
        }
        25 -> {//View Private
        }
        8 -> {//View Friends
        }
        22 -> {//View Clan
        }
        19 -> {//View Trade
        }
        16 -> {//View Assist
        }
    }
}

on(InterfaceOption, "All", id = FilterButtons) { _, _, _, _ ->
}

on(InterfaceOption, "Filter", id = FilterButtons) { _, _, _, _ ->
}

on(InterfaceOption, "Report Abuse", id = FilterButtons) { _, _, _, _ ->

}
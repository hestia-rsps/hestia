package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerList
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.variable.model.events.SendVariable
import worlds.gregs.hestia.core.display.variable.model.variable.StringMapVariable
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSettings
import worlds.gregs.hestia.core.script.on

StringMapVariable(1584, Variable.Type.VARP, true, mapOf(
        0 to "normal",
        1 to "curses"
)).register("prayer_list")

on<InterfaceOpened> {
    where { id == PrayerList }
    then {
        val quickPrayers = false
        entity perform SendVariable("prayer_list")
        entity perform SendVariable("prayer_points")
        entity send InterfaceSettings(PrayerList, if (quickPrayers) 42 else 8, 0, 29, 2)
    }
}

on<InterfaceInteraction> {
    where { id == PrayerList }
    then {
        when(component) {
            12 -> {}//Show/Hide stat adjustments
        }
    }
}
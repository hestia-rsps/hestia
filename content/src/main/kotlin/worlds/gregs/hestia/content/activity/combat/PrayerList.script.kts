package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.display.window.api.Variable
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PrayerList
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.events.variable.SendVariable
import worlds.gregs.hestia.core.display.window.model.variable.StringMapVariable
import worlds.gregs.hestia.network.client.encoders.messages.WidgetSettings


StringMapVariable(1584, Variable.Type.VARP, true, mapOf(
        0 to "normal",
        1 to "curses"
)).register("prayer_list")

on<WindowOpened> {
    where { target == PrayerList }
    then {
        val quickPrayers = false
        entity perform SendVariable("prayer_list")
        entity perform SendVariable("prayer_points")
        entity send WidgetSettings(PrayerList, if (quickPrayers) 42 else 8, 0, 29, 2)
    }
}

on<WindowInteraction> {
    where { target == PrayerList }
    then {
        when(widget) {
            12 -> {}//Show/Hide stat adjustments
        }
    }
}
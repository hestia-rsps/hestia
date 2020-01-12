package worlds.gregs.hestia.content.display.windows

import worlds.gregs.hestia.core.display.window.api.Variable
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.HealthOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PrayerOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SummoningOrb
import worlds.gregs.hestia.core.display.window.model.events.SendVariable
import worlds.gregs.hestia.core.display.window.model.events.ToggleVariable
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.variable.BooleanVariable
import worlds.gregs.hestia.core.display.window.model.variable.IntVariable
import worlds.gregs.hestia.core.display.window.model.variable.StringVariable
import worlds.gregs.hestia.network.client.encoders.messages.RunEnergy
import worlds.gregs.hestia.network.client.encoders.messages.Varp

IntVariable(7198, Variable.Type.VARBIT, true, 990).register("life_points")
IntVariable(2382, Variable.Type.VARP, true, 990).register("prayer_points")
BooleanVariable(181, Variable.Type.VARC).register("select_quick_prayers")
BooleanVariable(182, Variable.Type.VARC).register("using_quick_prayers")


StringVariable(1584, Variable.Type.VARP, true, "normal", mapOf(
        0 to "normal",
        1 to "curses"
)).register("prayer_list")
BooleanVariable(102, Variable.Type.VARP).register("poisoned")

on<WindowOpened> {
    where { target == HealthOrb }
    then {
        entity perform SendVariable("life_points")
        entity perform SendVariable("poisoned")
    }
}

on<WindowInteraction> {
    where { target == HealthOrb && widget == 1 }
    then {
        when(option) {
            1 -> {//Use cure (p)
            }
        }
    }
}

on<WindowOpened> {
    where { target == PrayerOrb }
    then {
        entity perform SendVariable("select_quick_prayers")
        entity perform SendVariable("using_quick_prayers")
    }
}

on<WindowInteraction> {
    where { target == PrayerOrb && widget == 1 }
    then {
        when(option) {
            1 -> entity perform ToggleVariable("using_quick_prayers")
            2 -> entity perform ToggleVariable("select_quick_prayers")
        }
    }
}

on<WindowOpened> {
    where { target == EnergyOrb }
    then {
        entity send RunEnergy(100)
    }
}

on<WindowOpened> {
    where { target == SummoningOrb }
    then {
        entity send Varp(1160, -1)
    }
}
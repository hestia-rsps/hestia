package worlds.gregs.hestia.content.display.windows

import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.HealthOrb
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerOrb
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SummoningOrb
import worlds.gregs.hestia.core.display.interfaces.model.events.variable.SendVariable
import worlds.gregs.hestia.core.display.interfaces.model.events.variable.ToggleVariable
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.variable.model.variable.BooleanVariable
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.core.display.variable.model.variable.StringMapVariable
import worlds.gregs.hestia.network.client.encoders.messages.RunEnergy
import worlds.gregs.hestia.network.client.encoders.messages.Varp

IntVariable(7198, Variable.Type.VARBIT, true, 990).register("life_points")
IntVariable(2382, Variable.Type.VARP, true, 990).register("prayer_points")
BooleanVariable(181, Variable.Type.VARC).register("select_quick_prayers")
BooleanVariable(182, Variable.Type.VARC).register("using_quick_prayers")


StringMapVariable(1584, Variable.Type.VARP, true, mapOf(
        0 to "normal",
        1 to "curses"
)).register("prayer_list")
BooleanVariable(102, Variable.Type.VARP).register("poisoned")

on<InterfaceOpened> {
    where { id == HealthOrb }
    then {
        entity perform SendVariable("life_points")
        entity perform SendVariable("poisoned")
    }
}

on<InterfaceInteraction> {
    where { id == HealthOrb && component == 1 }
    then {
        when(option) {
            1 -> {//Use cure (p)
            }
        }
    }
}

on<InterfaceOpened> {
    where { id == PrayerOrb }
    then {
        entity perform SendVariable("select_quick_prayers")
        entity perform SendVariable("using_quick_prayers")
    }
}

on<InterfaceInteraction> {
    where { id == PrayerOrb && component == 1 }
    then {
        when(option) {
            1 -> entity perform ToggleVariable("using_quick_prayers")
            2 -> entity perform ToggleVariable("select_quick_prayers")
        }
    }
}

on<InterfaceOpened> {
    where { id == EnergyOrb }
    then {
        entity perform SendVariable("energy_orb")
        entity send RunEnergy(100)
    }
}

on<InterfaceOpened> {
    where { id == SummoningOrb }
    then {
        entity send Varp(1160, -1)
    }
}
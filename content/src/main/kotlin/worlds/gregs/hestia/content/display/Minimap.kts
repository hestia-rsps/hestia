package worlds.gregs.hestia.content.display

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.HealthOrb
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SummoningOrb
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.model.events.SendVariable
import worlds.gregs.hestia.core.display.variable.model.variable.BooleanVariable
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.network.client.encoders.messages.RunEnergy
import worlds.gregs.hestia.network.client.encoders.messages.Varp

IntVariable(7198, Variable.Type.VARBIT, true, 990).register("life_points")

BooleanVariable(102, Variable.Type.VARP).register("poisoned")

Tabs// FIXME workaround

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
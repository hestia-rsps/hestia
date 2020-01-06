package worlds.gregs.hestia.content.display.windows

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.HealthOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SummoningOrb
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.ConfigFile
import worlds.gregs.hestia.network.client.encoders.messages.RunEnergy

on<WindowOpened> {
    where { target == HealthOrb }
    then {
        entity send ConfigFile(7198, 990)
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
        entity send Config(1160, -1)
    }
}
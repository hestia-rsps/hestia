package worlds.gregs.hestia.content.display.windows

import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.HealthOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SummoningOrb
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.ConfigFile
import worlds.gregs.hestia.network.client.encoders.messages.RunEnergy

lateinit var es: EventSystem

on<WindowOpened> {
    where { target == HealthOrb }
    then { (entityId, _) ->
        es.send(entityId, ConfigFile(7198, 990))
    }
}

on<WindowOpened> {
    where { target == EnergyOrb }
    then { (entityId, _) ->
        es.send(entityId, RunEnergy(100))
    }
}

on<WindowOpened> {
    where { target == SummoningOrb }
    then { (entityId, _) ->
        es.send(entityId, Config(1160, -1))
    }
}
package worlds.gregs.hestia.content.display.main

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Notes
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSettings
import worlds.gregs.hestia.core.script.on

on<InterfaceOpened> {
    where { id == Notes }
    then {
        entity send InterfaceSettings(Notes, 9, 0, 30, 2621470)
    }
}
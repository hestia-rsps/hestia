package worlds.gregs.hestia.content.display.windows.main

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Notes
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSettings

on<InterfaceOpened> {
    where { id == Notes }
    then {
        entity send InterfaceSettings(Notes, 9, 0, 30, 2621470)
    }
}
package worlds.gregs.hestia.content.display.main

import worlds.gregs.hestia.artemis.events.Disconnect
import worlds.gregs.hestia.core.display.client.model.components.ExitLobby
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Logout
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.script.on

on<InterfaceInteraction> {
    where { id == Logout }
    then {
        when (component) {
            6 -> {//Lobby
                entity create ExitLobby::class
                dispatch(Disconnect(entity))
            }
            13 -> dispatch(Disconnect(entity))//Login
        }
    }
}
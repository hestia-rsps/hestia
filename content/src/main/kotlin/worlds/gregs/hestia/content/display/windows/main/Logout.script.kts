package worlds.gregs.hestia.content.display.windows.main

import worlds.gregs.hestia.artemis.events.Disconnect
import worlds.gregs.hestia.core.display.client.model.components.ExitLobby
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Logout
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction

on<WindowInteraction> {
    where { target == Logout }
    task {
        val (_, _, component) = event(this)
        when (component) {
            6 -> {//Lobby
                entity create ExitLobby::class
                world dispatch Disconnect(entity)
            }
            13 -> world dispatch Disconnect(entity)//Login
        }
    }
}
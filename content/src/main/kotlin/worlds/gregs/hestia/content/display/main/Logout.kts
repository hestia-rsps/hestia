package worlds.gregs.hestia.content.display.main

import worlds.gregs.hestia.artemis.events.Disconnect
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.getInterfaceComponentId
import worlds.gregs.hestia.core.display.client.model.components.ExitLobby
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Logout
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ResizableGameframe

on(InterfaceOption, "Exit", ids = *intArrayOf(FixedGameframe, ResizableGameframe)) { _, _, _, _ ->
    entity perform OpenInterface(Logout)
}

on(InterfaceOption, "*", id = Logout) { hash, _, _, _ ->
    when (getInterfaceComponentId(hash)) {
        6 -> {//Lobby
            entity create ExitLobby::class
            dispatch(Disconnect(entity))
        }
        13 -> dispatch(Disconnect(entity))//Login
    }
}
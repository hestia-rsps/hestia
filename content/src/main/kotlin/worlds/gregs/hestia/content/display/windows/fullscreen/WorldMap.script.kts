package worlds.gregs.hestia.content.display.windows.fullscreen

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.WorldMap
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.network.client.encoders.messages.ConfigGlobal

on<WindowOpened> {
    where { target == WorldMap }
    then {
        val position = entity get Position::class
        val posHash = position.x shl 14 or position.y
        entity send ConfigGlobal(622, posHash)
        entity send ConfigGlobal(674, posHash)
    }
}

on<WindowInteraction> {
    where { target == WorldMap }
    then {
        if(widget == 44) {
            //Close button
        }
    }
}
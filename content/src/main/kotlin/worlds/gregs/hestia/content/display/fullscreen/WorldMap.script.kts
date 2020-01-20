package worlds.gregs.hestia.content.display.fullscreen

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WorldMap
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.network.client.encoders.messages.Varc

on<InterfaceOpened> {
    where { id == WorldMap }
    then {
        val position = entity get Position::class
        val posHash = position.x shl 14 or position.y
        entity send Varc(622, posHash)
        entity send Varc(674, posHash)
    }
}

on<InterfaceInteraction> {
    where { id == WorldMap }
    then {
        if(component == 44) {
            //Close button
        }
    }
}
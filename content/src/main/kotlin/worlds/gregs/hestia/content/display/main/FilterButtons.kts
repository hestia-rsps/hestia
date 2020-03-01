package worlds.gregs.hestia.content.display.main

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FilterButtons
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.script.on

on<InterfaceInteraction> {
    where { id == FilterButtons }
    then {
        when (component) {
            31 -> {//Regular chat
                when (option) {
                    1 -> {//View
                    }
                    2 -> {//All game
                    }
                    4 -> {//Filter game
                    }
                }
            }
            25 -> {//Private chat
            }
            19 -> {//Trade chat
            }
            16 -> {//Assist chat
            }
            13 -> {//Report abuse
            }
        }
    }
}
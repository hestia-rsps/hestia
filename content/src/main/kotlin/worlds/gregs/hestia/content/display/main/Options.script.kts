package worlds.gregs.hestia.content.display.main

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.GraphicsOptions
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Options
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SoundOptions
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction

on<InterfaceInteraction> {
    where { id == Options }
    then {
        when (component) {
            14 -> entity perform OpenInterface(GraphicsOptions)
            16 -> entity perform OpenInterface(SoundOptions)
            6 -> {//Mouse buttons
            }
            3 -> {//Profanity filter
            }
            4 -> {//Chat effects
            }
            5 -> {//Chat setup
            }
            7 -> {//Accept aid
            }
            8 -> {//House options
            }
            18 -> {//Adventures log options
            }
        }
    }
}
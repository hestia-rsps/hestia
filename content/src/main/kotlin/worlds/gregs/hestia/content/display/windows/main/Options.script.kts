package worlds.gregs.hestia.content.display.windows.main

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.GraphicsOptions
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Options
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SoundOptions
import worlds.gregs.hestia.core.display.window.model.actions.OpenWindow
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction

on<WindowInteraction> {
    where { target == Options }
    then {
        when (widget) {
            14 -> entity perform OpenWindow(GraphicsOptions)
            16 -> entity perform OpenWindow(SoundOptions)
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
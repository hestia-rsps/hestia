package worlds.gregs.hestia.content.display.windows.main

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FilterButtons
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction

on<WindowInteraction> {
    where { target == FilterButtons }
    task {
        val (_, _, widget, _, _, option) = event(this)
        when(widget) {
            31 -> {//Regular chat
            when(option) {
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
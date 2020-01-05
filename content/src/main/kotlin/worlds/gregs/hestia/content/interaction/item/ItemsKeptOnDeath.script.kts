package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ItemsKeptOnDeath
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction

on<WindowInteraction> {
    where { target == ItemsKeptOnDeath }
    task {
        val (_, _, widget, _, _, _) = event(this)
        when(widget) {
            28 -> {//What if I enter the wilderness?
            }
        }
    }
}


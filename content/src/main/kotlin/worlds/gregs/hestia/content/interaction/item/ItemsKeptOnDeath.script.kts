package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ItemsKeptOnDeath
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction

on<WindowInteraction> {
    where { target == ItemsKeptOnDeath }
    task {
        val (_, _, component, _, _, _) = event(this)
        when(component) {
            28 -> {//What if I enter the wilderness?
            }
        }
    }
}


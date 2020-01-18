package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ItemsKeptOnDeath
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction

on<InterfaceInteraction> {
    where { id == ItemsKeptOnDeath }
    then {
        when(component) {
            28 -> {//What if I enter the wilderness?
            }
        }
    }
}


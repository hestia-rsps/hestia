package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ItemsKeptOnDeath

on(InterfaceOption, "Toggle", id = ItemsKeptOnDeath) { _, _, _, _ ->
    //What if I enter the wilderness?
}
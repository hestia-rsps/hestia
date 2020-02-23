package worlds.gregs.hestia.content.interaction

import worlds.gregs.hestia.content.interaction.item.EquipmentAction
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.content.interaction.item.InventoryAction

on<InventoryAction> {
    where { option == "Examine" }
    then {
        entity perform Chat("It's ${item.definition().name}.")//Temp
    }
}

on<EquipmentAction> {
    where { option == "Examine" }
    then {
        entity perform Chat("It's ${item.definition().name}.")//Temp
    }
}
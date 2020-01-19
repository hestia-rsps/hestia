package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.EquipmentBonuses
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ItemsKeptOnDeath
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PriceChecker
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WornEquipment
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceRefresh
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItems

on<InterfaceOpened> {
    where { id == WornEquipment }
    then {
        sendItems()
    }
}

on<InterfaceRefresh> {
    where { id == WornEquipment }
    then {
        sendItems()//TODO send just changes?
    }
}

fun EntityAction.sendItems() {
    entity send InterfaceItems(94, (0 until 15).map { Pair(it, 1) })
}

on<InterfaceInteraction> {
    where { id == WornEquipment }
    then {
        when(component) {
            8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 38, 50 -> {
                val index = if(component == 50) 11 else (component - 8) / 3
                when(option) {
                    1 -> {}//Remove
                    8 -> {}//Examine
                }
            }
            39 -> entity perform OpenInterface(EquipmentBonuses)
            42 -> entity perform OpenInterface(PriceChecker)
            45 -> entity perform OpenInterface(ItemsKeptOnDeath)
        }
    }
}

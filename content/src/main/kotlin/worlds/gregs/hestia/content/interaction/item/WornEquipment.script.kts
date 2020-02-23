package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.EquipmentBonuses
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ItemsKeptOnDeath
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PriceChecker
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WornEquipment
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceRefresh
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItems
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_AURA
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_FEET
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_HANDS
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_RING
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_LEGS

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
    val items = (entity container ContainerType.EQUIPMENT).map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}
    entity send InterfaceItems(94, items)
}

lateinit var containers: ContainerSystem
lateinit var definitions: ItemDefinitionSystem

on<InterfaceInteraction> {
    where { id == WornEquipment }
    then {
        when(component) {
            8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 38, 50 -> {
                val slot = when (component) {
                    50 -> SLOT_AURA
                    35 -> SLOT_RING
                    32 -> SLOT_FEET
                    29 -> SLOT_HANDS
                    26 -> SLOT_LEGS
                    else -> (component - 8) / 3
                }
                val item = (entity container ContainerType.EQUIPMENT).getOrNull(slot) ?: return@then
                val definition = definitions.get(item.type)
                var equipOption = definition.getEquipOption(option - 2) ?: definition.options.getOrNull(option) ?: if(option == 8) "Examine" else throw IllegalArgumentException("Unhandled equipment option $item - $option")
                if(equipOption == "Wield" || equipOption == "Wear") {
                    equipOption = "Remove"
                }
                entity perform EquipmentAction(item, slot, equipOption)
            }
            39 -> entity perform OpenInterface(EquipmentBonuses)
            42 -> entity perform OpenInterface(PriceChecker)
            45 -> entity perform OpenInterface(ItemsKeptOnDeath)
        }
    }
}

on<EquipmentAction> {
    where { option == "Remove" }
    then {
        val result = containers.modify(entity, ContainerType.INVENTORY to add(item.type, item.amount), ContainerType.EQUIPMENT to remove(item.type, item.amount))
        when(result) {
            ItemResult.Success.Success -> {
                entity perform UpdateAppearance()
            }
            ItemResult.Issue.Invalid -> TODO()
            ItemResult.Issue.Full -> {
                entity perform Chat("Inv Full")//FIXME
            }
        }
    }
}

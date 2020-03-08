package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.EquipmentBonuses
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ItemsKeptOnDeath
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PriceChecker
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WornEquipment
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
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
import world.gregs.hestia.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.core.action.logic.systems.getInterfaceComponentId
import worlds.gregs.hestia.core.action.model.*
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerSystem
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_ARROWS
import worlds.gregs.hestia.core.entity.item.container.logic.add
import worlds.gregs.hestia.core.entity.item.container.logic.remove
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType
import worlds.gregs.hestia.core.script.on

lateinit var containers: ContainerSystem
lateinit var definitions: ItemDefinitionSystem

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

worlds.gregs.hestia.core.action.logic.systems.on(InterfaceOption, "Show Equipment Stats", id = WornEquipment) { _, _, _, _ ->
    entity perform OpenInterface(EquipmentBonuses)
}

worlds.gregs.hestia.core.action.logic.systems.on(InterfaceOption, "Show Price-checker", id = WornEquipment) { _, _, _, _ ->
    entity perform OpenInterface(PriceChecker)
}

worlds.gregs.hestia.core.action.logic.systems.on(InterfaceOption, "Show Items Kept on Death", id = WornEquipment) { _, _, _, _ ->
    entity perform OpenInterface(ItemsKeptOnDeath)
}

worlds.gregs.hestia.core.action.logic.systems.on(InterfaceOption, "*", id = WornEquipment) { hash, _, _, option ->
    val slot = getSlot(hash)
    val item = (entity container ContainerType.EQUIPMENT).getOrNull(slot) ?: return@on
    val definition = definitions.get(item.type)
    var equipOption = definition.getEquipOption(option - 2) ?: definition.options.getOrNull(option)
    ?: throw IllegalArgumentException("Unhandled equipment option $item - $option")
    if (equipOption == "Wield" || equipOption == "Wear") {
        equipOption = "Remove"
    }
    entity perform EquipmentAction(item, slot, equipOption)
}

worlds.gregs.hestia.core.action.logic.systems.on(InterfaceOption, option = 8, id = WornEquipment) { hash, _, _, _ ->
    val slot = getSlot(hash)
    val item = (entity container ContainerType.EQUIPMENT).getOrNull(slot) ?: return@on
    entity perform EquipmentAction(item, slot, "Examine")
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
                entity perform Chat("You don't have enough inventory space.")
            }
        }
    }
}

fun getSlot(hash: Int) = when (val component = getInterfaceComponentId(hash)) {
    50 -> SLOT_AURA
    38 -> SLOT_ARROWS
    35 -> SLOT_RING
    32 -> SLOT_FEET
    29 -> SLOT_HANDS
    26 -> SLOT_LEGS
    else -> (component - 8) / 3
}

fun ItemDefinition.getEquipOption(optionId: Int): String? {
    return params?.getOrDefault(528L + optionId, null) as? String
}
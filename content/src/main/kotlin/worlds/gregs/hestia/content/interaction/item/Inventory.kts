package worlds.gregs.hestia.content.interaction.item

import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Inventory
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceRefresh
import worlds.gregs.hestia.core.display.interfaces.model.events.ComponentSwitch
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItems
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSettings
import worlds.gregs.hestia.core.entity.item.container.api.validateItem
import worlds.gregs.hestia.core.entity.item.container.api.validateSlot
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.equipSlots
import worlds.gregs.hestia.content.activity.combat.equipment.EquippedItem
import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType.INVENTORY
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType.EQUIPMENT
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.entity.item.container.api.Container
import worlds.gregs.hestia.core.entity.item.container.logic.*
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_SHIELD
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_WEAPON
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.TWO_HANDED
import worlds.gregs.hestia.core.entity.item.container.model.Item

val logger = LoggerFactory.getLogger(this::class.java)!!

on<InterfaceOpened> {
    where { id == Inventory }
    then {
        entity send InterfaceSettings(Inventory, 0, 0, 27, 4554126)//Item slots
        entity send InterfaceSettings(Inventory, 0, 28, 55, 2097152)//Draggable slots

        val items = (entity container INVENTORY).map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}
        entity send InterfaceItems(93, items)
    }
}

on<InterfaceRefresh> {
    where { id == Inventory }
    then {
        val items = (entity container INVENTORY).map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount) }
        entity send InterfaceItems(93, items)
    }
}

lateinit var containers: ContainerSystem

on(InterfaceOption, Inventory) { ->
    fun EntityActions.task(hash: Int, type: Int, slot: Int, option: Int) = queue {
        val inventory = entity container INVENTORY
        val item = inventory.validateItem(slot, type) ?: return@queue logger.warn("Invalid item slot $slot $type $option")
        val definition = item.definition()
        entity perform InventoryAction(item, slot, when(option) {
            8 -> if(definition.options.any { it == "Destroy" }) "Destroy" else "Drop"
            10 -> "Examine"
            else -> definition.options.getOrNull(option - 1) ?: return@queue logger.warn("Unknown item option $item $option")
        })
    }
    then(EntityActions::task)
}

on<InventoryAction> {
    where { option == "Drop" }
    then {
        when(entity inventory remove(item.type, item.amount, slot)) {
            is ItemResult.Success -> entity drop item
            else -> logger.warn("Issue dropping item $item $slot")
        }
    }
}

on<InventoryAction> {
    where { option == "Wield" || option == "Wear" }
    then {
        val equipSlot = equipSlots.getOrDefault(item.type, -1)
        val equipment = entity container EQUIPMENT
        val current = equipment.getOrNull(equipSlot)

        val equipmentChange = CompositionBuilder()
        val inventoryChange = CompositionBuilder()

        // Remove shield if two handed weapon
        val conflict = equipment.getConflictingOffhand(item, equipSlot)

        // Remove conflicting equipment
        if(current != null) {
            equipmentChange += remove(current.type, current.amount, equipSlot)
        }
        if(conflict != null) {
            equipmentChange += remove(conflict.type, conflict.amount, if(equipSlot == SLOT_SHIELD) SLOT_WEAPON else SLOT_SHIELD)
        }

        // Equip
        equipmentChange += add(item.type, item.amount, equipSlot)
        inventoryChange += remove(item.type, item.amount, slot)

        // Add conflicts to inventory
        if(current != null) {
            inventoryChange += add(current.type, current.amount, slot)
        }
        if(conflict != null) {
            inventoryChange += add(conflict.type, conflict.amount)
        }

        val result = containers.modify(entity, INVENTORY to inventoryChange.build(), EQUIPMENT to equipmentChange.build())
        when(result) {
            is ItemResult.Success -> {
                entity perform EquippedItem(item)
                entity perform UpdateAppearance()
            }
            is ItemResult.Issue.Full -> entity perform Chat("Your inventory is full.")
            else -> TODO("")
        }
    }
}

lateinit var equipment: EquipmentSystem

fun Container.getConflictingOffhand(item: Item, equipSlot: Int): Item? {
    if(equipSlot == SLOT_WEAPON || equipSlot == SLOT_SHIELD) {
        val weapon = if(equipSlot == SLOT_WEAPON) item else getOrNull(SLOT_WEAPON)
        if(weapon != null && equipment.getEquipType(weapon) == TWO_HANDED) {
            return if(equipSlot == SLOT_SHIELD) getOrNull(SLOT_WEAPON) else getOrNull(SLOT_SHIELD)
        }
    }
    return null
}

on<ComponentSwitch> {
    where { fromWindow == Inventory && toWindow == Inventory }
    fun ComponentSwitch.task() = queue {
        val (_, fromSlot, _, _, toIndex, toType) = this@task
        val toSlot = toIndex - 28

        val inventory = entity container INVENTORY
        inventory.validateSlot(fromSlot)//fromType is always -1
        inventory.validateItem(toSlot, toType)

        entity inventory switch(fromSlot, toSlot)
        //Ignore results
    }
    then(ComponentSwitch::task)
}
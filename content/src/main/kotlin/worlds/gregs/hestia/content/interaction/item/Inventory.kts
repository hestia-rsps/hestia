package worlds.gregs.hestia.content.interaction.item

import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Inventory
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
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
import arrow.core.andThen
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerSystem
import worlds.gregs.hestia.core.entity.item.container.logic.add
import worlds.gregs.hestia.core.entity.item.container.logic.remove
import worlds.gregs.hestia.core.entity.item.container.logic.switch
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType.INVENTORY
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType.EQUIPMENT
import worlds.gregs.hestia.core.script.on

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

on<InterfaceInteraction> {
    where { id == Inventory }
    fun InterfaceInteraction.task() = queue {
        val (_, _, type, slot, option) = this@task
        val inventory = entity container INVENTORY
        val item = inventory.validateItem(slot, type) ?: return@queue logger.warn("Invalid item slot $slot $type $option")
        val definition = item.definition()
        entity perform InventoryAction(item, slot, when(option) {
            7 -> if(definition.options.any { it == "Destroy" }) "Destroy" else "Drop"
            8 -> "Examine"
            else -> definition.options.getOrNull(option - 1) ?: return@queue logger.warn("Unknown item option $item $option")
        })
    }
    then(InterfaceInteraction::task)
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
        val result = if(current != null) {
            val replaceCurrent = remove(current.type, current.amount, equipSlot) andThen add(item.type, item.amount, equipSlot)
            val replaceEquip = remove(item.type, item.amount, slot) andThen add(current.type, current.amount, slot)
            containers.modify(entity, EQUIPMENT to replaceCurrent, INVENTORY to replaceEquip)
        } else {
            containers.modify(entity, INVENTORY to remove(item.type, item.amount, slot), EQUIPMENT to add(item.type, item.amount, equipSlot))
        }
        println("Wield result $result")
        when(result) {
            is ItemResult.Success -> entity perform UpdateAppearance()
            else -> TODO("")
        }
    }
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
package worlds.gregs.hestia.content.interaction.item

import org.slf4j.LoggerFactory
import worlds.gregs.hestia.content.interaction.item.Inventory_script.Inv
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

val logger = LoggerFactory.getLogger(Inventory_script::class.java)!!

private typealias Inv = worlds.gregs.hestia.core.entity.item.container.model.Inventory
on<InterfaceOpened> {
    where { id == Inventory }
    then {
        entity send InterfaceSettings(Inventory, 0, 0, 27, 4554126)//Item slots
        entity send InterfaceSettings(Inventory, 0, 28, 55, 2097152)//Draggable slots
        val items = entity.get(Inv::class).items.map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}
        entity send InterfaceItems(93, items)
    }
}

on<InterfaceRefresh> {
    where { id == Inventory }
    then {
        val items = entity.get(Inv::class).items.map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}
        entity send InterfaceItems(93, items)
    }
}

on<InterfaceInteraction> {
    where { id == Inventory }
    fun InterfaceInteraction.task() = queue {
        val (_, _, type, slot, option) = this@task
        val inventory = entity.get(Inv::class)
        val item = inventory.items.validateItem(slot, type) ?: return@queue logger.warn("Invalid item slot $slot $type $option")

        val choice = item.definition().options.getOrNull(option - 1)

        when {
            choice != null -> {
                println("Inventory action $choice $type $slot")
            }
            option == 7 -> {//Drop
                when(inventory transform remove(item.type, item.amount, slot)) {
                    is ItemResult.Success -> entity drop item
                    else -> logger.warn("Issue dropping item ${inventory.items} $item $slot")
                }
            }
            option == 8 -> {//Examine
                entity perform Chat("It's ${item.definition().name}.")//Temp
            }
            else -> logger.warn("Unknown item option $item $option")
        }
    }
    then(InterfaceInteraction::task)
}

on<ComponentSwitch> {
    where { fromWindow == Inventory && toWindow == Inventory }
    fun ComponentSwitch.task() = queue {
        val (_, fromSlot, _, _, toIndex, toType) = this@task
        val toSlot = toIndex - 28

        val inventory = entity.inventory()
        inventory.items.validateSlot(fromSlot)//fromType is always -1
        inventory.items.validateItem(toSlot, toType)

        inventory transform switch(fromSlot, toSlot)
        //Ignore results
    }
    then(ComponentSwitch::task)
}
package worlds.gregs.hestia.content.interaction.item

import org.slf4j.LoggerFactory
import worlds.gregs.hestia.content.interaction.item.Inventory_script.Inv
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Inventory
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.events.WindowRefresh
import worlds.gregs.hestia.core.display.window.model.events.WindowSwitch
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItems
import worlds.gregs.hestia.network.client.encoders.messages.WidgetSettings

val logger = LoggerFactory.getLogger(Inventory_script::class.java)!!

private typealias Inv = worlds.gregs.hestia.core.entity.item.container.model.Inventory
on<WindowOpened> {
    where { target == Inventory }
    then {
        entity send WidgetSettings(Inventory, 0, 0, 27, 4554126)//Item slots
        entity send WidgetSettings(Inventory, 0, 28, 55, 2097152)//Draggable slots
        val items = entity.get(Inv::class).items.map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}
        entity send WidgetItems(93, items)
    }
}

on<WindowRefresh> {
    where { target == Inventory }
    then {
        val items = entity.get(Inv::class).items.map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}
        entity send WidgetItems(93, items)
    }
}

on<WindowInteraction> {
    where { target == Inventory }
    fun WindowInteraction.task() = queue {
        val (_, _, type, slot, option) = this@task
        val inventory = entity.get(Inv::class)
        val item = inventory.validateItem(slot, type)

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
    then(WindowInteraction::task)
}

on<WindowSwitch> {
    where { fromWindow == Inventory && toWindow == Inventory }
    fun WindowSwitch.task() = queue {
        val (_, fromSlot, _, _, toIndex, toType) = this@task
        val toSlot = toIndex - 28

        val inventory = entity.inventory()
        inventory.validateSlot(fromSlot)//fromType is always -1
        inventory.validateItem(toSlot, toType)

        inventory transform switch(fromSlot, toSlot)
        //Ignore results
    }
    then(WindowSwitch::task)
}
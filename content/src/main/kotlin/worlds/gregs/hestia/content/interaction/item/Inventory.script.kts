package worlds.gregs.hestia.content.interaction.item

import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Inventory
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.events.WindowRefresh
import worlds.gregs.hestia.core.display.window.model.events.WindowSwitch
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentSettings
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItems

val logger = LoggerFactory.getLogger(Inventory_script::class.java)!!

on<WindowOpened> {
    where { target == Inventory }
    task {
        entity send WidgetComponentSettings(Inventory, 0, 0, 27, 4554126)//Item slots
        entity send WidgetComponentSettings(Inventory, 0, 28, 55, 2097152)//Draggable slots
        val items = entity.inventory().items.map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}
        entity send WidgetItems(93, items)
    }
}

on<WindowRefresh> {
    where { target == Inventory }
    task {
        val items = entity.inventory().items.map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}
        entity send WidgetItems(93, items)
    }
}

on<WindowInteraction> {
    where { target == Inventory }
    task {
        val (_, _, _, type, slot, option) = event(this)

        val inventory = entity.inventory()
        val item = inventory.validateItem(slot, type)!!

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
                entity message "It's ${item.definition().name}."//Temp
            }
            else -> logger.warn("Unknown item option $item $option")
        }
    }
}

on<WindowSwitch> {
    where { fromWindow == Inventory && toWindow == Inventory }
    task {
        val (_, _, fromSlot, _, _, toIndex, toType) = event(this)
        val toSlot = toIndex - 28

        val inventory = entity.inventory()
        inventory.validateSlot(fromSlot)//fromType is always -1
        inventory.validateItem(toSlot, toType)

        inventory transform switch(fromSlot, toSlot)
        //Ignore results
    }
}
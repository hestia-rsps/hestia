package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.EquipmentBonuses
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ItemsKeptOnDeath
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PriceChecker
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.WornEquipment
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.events.WindowRefresh
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItems

on<WindowOpened> {
    where { target == WornEquipment }
    task {
        sendItems()
    }
}

on<WindowRefresh> {
    where { target == WornEquipment }
    task {
        sendItems()//TODO send just changes?
    }
}

fun Task.sendItems() {
    entity send WidgetItems(94, (0 until 15).map { Pair(it, 1) })
}

on<WindowInteraction> {
    where { target == WornEquipment }
    task {
        val (_, _, widget, _, _, option) = event(this)
        when(widget) {
            8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 38, 50 -> {
                val index = if(widget == 50) 11 else (widget - 8) / 3
                when(option) {
                    1 -> {}//Remove
                    8 -> {}//Examine
                }
            }
            39 -> entity openWindow EquipmentBonuses
            42 -> entity openWindow PriceChecker
            45 -> entity openWindow ItemsKeptOnDeath
        }
    }
}

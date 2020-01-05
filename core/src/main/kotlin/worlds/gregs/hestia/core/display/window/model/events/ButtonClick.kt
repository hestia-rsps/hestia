package worlds.gregs.hestia.core.display.window.model.events

import net.mostlyoriginal.api.event.common.Event

data class ButtonClick(val entityId: Int, val interfaceHash: Int, val fromSlot: Int, val toSlot: Int, val option: Int) : Event {
    val widgetId: Int
        get() = interfaceHash shr 16
    val componentId: Int
        get() = interfaceHash - (widgetId shl 16)
}
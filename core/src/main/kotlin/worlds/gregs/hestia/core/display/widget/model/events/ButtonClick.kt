package worlds.gregs.hestia.core.display.widget.model.events

import net.mostlyoriginal.api.event.common.Event

class ButtonClick(val entityId: Int, val interfaceHash: Int, val option: Int) : Event{
    val widgetId: Int
        get() = interfaceHash shr 16
    val componentId: Int
        get() = interfaceHash - (widgetId shl 16)
}
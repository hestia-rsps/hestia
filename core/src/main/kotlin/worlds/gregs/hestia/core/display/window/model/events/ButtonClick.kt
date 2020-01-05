package worlds.gregs.hestia.core.display.window.model.events

import net.mostlyoriginal.api.event.common.Event

data class ButtonClick(val entityId: Int, val hash: Int, val fromSlot: Int, val toSlot: Int, val option: Int) : Event
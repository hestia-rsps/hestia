package worlds.gregs.hestia.game.events

import net.mostlyoriginal.api.event.common.Event

class ButtonClick(val entityId: Int, val widgetId: Int, val componentId: Int, val option: Int) : Event
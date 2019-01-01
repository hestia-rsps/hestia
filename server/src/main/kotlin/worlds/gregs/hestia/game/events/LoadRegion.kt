package worlds.gregs.hestia.game.events

import net.mostlyoriginal.api.event.common.Event

data class LoadRegion(val entityId: Int, val regionId: Int) : Event
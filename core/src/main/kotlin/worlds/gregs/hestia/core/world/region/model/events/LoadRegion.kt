package worlds.gregs.hestia.core.world.region.model.events

import net.mostlyoriginal.api.event.common.Event

data class LoadRegion(val entityId: Int, val regionId: Int) : Event
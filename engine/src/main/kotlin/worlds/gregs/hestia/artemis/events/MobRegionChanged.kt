package worlds.gregs.hestia.artemis.events

import net.mostlyoriginal.api.event.common.Event

data class MobRegionChanged(val entityId: Int, val from: Int?, val to: Int?) : Event
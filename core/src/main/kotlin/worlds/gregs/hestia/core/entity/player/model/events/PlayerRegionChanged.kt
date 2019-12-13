package worlds.gregs.hestia.core.entity.player.model.events

import net.mostlyoriginal.api.event.common.Event

data class PlayerRegionChanged(val entityId: Int, val from: Int?, val to: Int?) : Event
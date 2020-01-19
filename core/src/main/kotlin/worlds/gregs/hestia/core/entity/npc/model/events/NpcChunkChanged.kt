package worlds.gregs.hestia.core.entity.npc.model.events

import net.mostlyoriginal.api.event.common.Event

data class NpcChunkChanged(val entityId: Int, val from: Int?, val to: Int?) : Event
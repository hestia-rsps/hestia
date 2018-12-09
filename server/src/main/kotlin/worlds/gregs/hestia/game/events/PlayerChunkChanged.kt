package worlds.gregs.hestia.game.events

import net.mostlyoriginal.api.event.common.Event

data class PlayerChunkChanged(val entityId: Int, val from: Int?, val to: Int?) : Event
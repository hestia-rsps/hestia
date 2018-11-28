package worlds.gregs.hestia.game.events

import net.mostlyoriginal.api.event.common.Event

data class CreateMob(val mobId: Int, val x: Int, val y: Int, val plane: Int = 0) : Event
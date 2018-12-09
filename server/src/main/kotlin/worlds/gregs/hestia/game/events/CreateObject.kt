package worlds.gregs.hestia.game.events

import net.mostlyoriginal.api.event.common.Event

data class CreateObject(val objectId: Int, val x: Int, val y: Int, val plane: Int = 0, val type: Int, val rotation: Int) : Event
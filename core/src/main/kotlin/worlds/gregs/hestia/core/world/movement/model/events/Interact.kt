package worlds.gregs.hestia.core.world.movement.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class Interact(val target: Int, val partial: Boolean = false) : EntityAction(), InstantEvent
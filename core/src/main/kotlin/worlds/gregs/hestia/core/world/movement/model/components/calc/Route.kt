package worlds.gregs.hestia.core.world.movement.model.components.calc

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class Route(val target: Int, val alternative: Boolean = false) : EntityAction(), InstantEvent
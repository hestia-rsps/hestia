package worlds.gregs.hestia.core.world.movement.model.events

import worlds.gregs.hestia.core.action.WorldEvent

data class Moved(val entity: Int) : WorldEvent()
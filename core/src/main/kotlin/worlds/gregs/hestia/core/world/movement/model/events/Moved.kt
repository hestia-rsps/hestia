package worlds.gregs.hestia.core.world.movement.model.events

import worlds.gregs.hestia.core.task.api.event.EntityEvent

data class Moved(override val entity: Int) : EntityEvent
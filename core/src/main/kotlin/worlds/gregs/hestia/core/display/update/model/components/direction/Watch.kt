package worlds.gregs.hestia.core.display.update.model.components.direction

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class Watch(val target: Int = -1) : EntityAction(), InstantEvent
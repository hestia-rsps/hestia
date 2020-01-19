package worlds.gregs.hestia.core.display.update.model.components.direction

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.update.model.Direction

data class Turn(val direction: Direction) : EntityAction(), InstantEvent
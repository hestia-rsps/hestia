package worlds.gregs.hestia.core.display.dialogue.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

data class IntegerEntered(val integer: Int): EntityAction(), InstantEvent
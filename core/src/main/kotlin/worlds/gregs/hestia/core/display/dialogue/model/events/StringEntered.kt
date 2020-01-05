package worlds.gregs.hestia.core.display.dialogue.model.events

import worlds.gregs.hestia.core.task.api.event.EntityEvent

data class StringEntered(override val entity: Int, val string: String): EntityEvent
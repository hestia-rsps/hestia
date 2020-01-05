package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.task.api.event.TargetEvent

data class WindowRefresh(override val entity: Int, override val target: Int) : TargetEvent, InstantEvent
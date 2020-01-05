package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.task.api.event.TargetEvent

/**
 * @param silent If silent then no client packets should be sent
 */
data class WindowClosed(override val entity: Int, override val target: Int, val silent: Boolean) : TargetEvent, InstantEvent
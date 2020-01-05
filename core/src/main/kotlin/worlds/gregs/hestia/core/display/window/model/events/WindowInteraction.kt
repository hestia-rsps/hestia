package worlds.gregs.hestia.core.display.window.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.task.api.event.TargetEvent

/**
 * A client interaction with a window component
 * @param entity The entity doing the interaction
 * @param target The targeted window id
 * @param component The targeted window component
 * @param first First parameter
 * @param second Second parameter
 * @param option Option parameter
 */
data class WindowInteraction(override val entity: Int, override val target: Int, val component: Int, val first: Int, val second: Int, val option: Int) : TargetEvent, InstantEvent
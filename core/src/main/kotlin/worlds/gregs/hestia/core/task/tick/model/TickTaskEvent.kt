package worlds.gregs.hestia.core.task.tick.model

import com.artemis.World
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.getSystem

/**
 * This is technically a separate system from [Task] but it's the most relevant location
 */
class TickTaskEvent(val delay: Int, val period: Int, val task: TickTask.() -> Unit) : Event

fun World.schedule(delay: Int, period: Int, task: TickTask.() -> Unit) {
    getSystem(EventSystem::class).dispatch(TickTaskEvent(delay, period, task))
}
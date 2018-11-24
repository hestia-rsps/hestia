package worlds.gregs.hestia.game.events

import com.artemis.Entity
import worlds.gregs.hestia.game.TickTask
import worlds.gregs.hestia.services.getSystem
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem

class TaskEvent(val delay: Int, val period: Int, val task: TickTask.() -> Unit) : Event

fun Entity.schedule(delay: Int, period: Int, task: TickTask.() -> Unit) {
    val eventSystem = world.getSystem(EventSystem::class)
    eventSystem.dispatch(TaskEvent(delay, period, task))
}
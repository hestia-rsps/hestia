package worlds.gregs.hestia.artemis.events

import com.artemis.World
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.TickTask
import worlds.gregs.hestia.services.getSystem

class TickTaskEvent(val delay: Int, val period: Int, val task: TickTask.() -> Unit) : Event

fun World.schedule(delay: Int, period: Int, task: TickTask.() -> Unit) {
    getSystem(EventSystem::class).dispatch(TickTaskEvent(delay, period, task))
}
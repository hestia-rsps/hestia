package worlds.gregs.hestia.game.events

import com.artemis.Entity
import worlds.gregs.hestia.services.getSystem
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.update.Marker

class Hit(val entityId: Int, val amount: Int, val mark: Int, val delay: Int = 0, var critical: Boolean = false, val source: Int = -1, val soak: Int = -1) : Event

fun Entity.hit(amount: Int, mark: Int = if(amount == 0) Marker.MISSED else Marker.REGULAR, delay: Int = 0, critical: Boolean = false, source: Int = -1, soak: Int = -1) {
    val eventSystem = world.getSystem(EventSystem::class)
    eventSystem.dispatch(Hit(this.id, amount, mark, delay, critical, source, soak))
}
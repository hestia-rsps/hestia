package worlds.gregs.hestia.core.action

import com.artemis.BaseSystem
import com.artemis.World
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.getSystem
import kotlin.reflect.KClass

infix fun <T : BaseSystem> ActionContext.system(c: KClass<T>): T {
    return world.getSystem(c.java)
}

infix fun World.dispatch(event: Event) {
    getSystem(EventSystem::class).dispatch(event)
}
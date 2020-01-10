package worlds.gregs.hestia.artemis.event

import com.artemis.utils.reflect.ClassReflection
import net.mostlyoriginal.api.event.common.Cancellable
import net.mostlyoriginal.api.event.common.Event
import kotlin.reflect.KClass

data class ExtendedEventListener(val event: KClass<out Event>, val priority: Int, val skipCancelledEvents: Boolean, val conditional: (Event.() -> Boolean)?, val action: Event.() -> Unit) {

    fun handle(event: Event) {
        if (skipCancelledEvents) {
            if (ClassReflection.isInstance(Cancellable::class.java, event) && (event as Cancellable).isCancelled) {
                // event can be cancelled, so do not submit!
                return
            }
        }

        try {
            if(conditional == null || conditional.invoke(event)) {
                action.invoke(event)
            }
        } catch (e: Exception) {
            throw RuntimeException("Could not call event.", e)
        }
    }
}
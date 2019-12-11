package worlds.gregs.hestia.artemis.event

import com.artemis.utils.reflect.ClassReflection
import net.mostlyoriginal.api.event.common.Cancellable
import net.mostlyoriginal.api.event.common.Event
import worlds.gregs.hestia.artemis.dsl.ArtemisEventListener

class ExtendedEventListener(private val priority: Int, private val skipCancelledEvents: Boolean, private val conditional: ((Event) -> Boolean)?, private val action: (Event) -> Unit) : Comparable<ExtendedEventListener> {

    constructor(listener: ArtemisEventListener) : this(listener.priority, listener.skipCancelledEvents, listener.conditional, listener.action)

    fun handle(event: Event?) {
        if (event == null) throw NullPointerException("Event required.")

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

    override fun compareTo(other: ExtendedEventListener): Int {
        return other.priority - priority
    }

}
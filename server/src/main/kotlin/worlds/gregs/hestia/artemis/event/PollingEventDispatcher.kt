package worlds.gregs.hestia.artemis.event

import com.artemis.utils.reflect.ClassReflection
import com.artemis.utils.reflect.ReflectionException
import net.mostlyoriginal.api.event.common.Event
import worlds.gregs.hestia.artemis.InstantEvent
import java.util.*

/**
 * Queues events and dispatches all in one go
 * Differs from [net.mostlyoriginal.api.event.dispatcher.PollingEventDispatcher] as
 * events dispatched during a dispatch are queued for the next cycle
 */
class PollingEventDispatcher : ExtendedFastEventDispatcher() {
    private val eventQueue = LinkedList<Event>()

    override fun process() {
        //Static size as only events for this cycle should be processed
        val size = eventQueue.size
        var i = 0
        while(i < size) {
            val event = eventQueue.poll()
            if(event != null) {
                super.dispatch(event)
            }
            i++
        }
    }

    override fun dispatch(event: Event) {
        if(event is InstantEvent) {
            super.dispatch(event)
        } else {
            eventQueue.add(event)
        }
    }

    override fun <T : Event> dispatch(type: Class<T>?): T {
        val event: T
        try {
            event = ClassReflection.newInstance(type!!) as T
            this.dispatch(event)
        } catch (e: ReflectionException) {
            val error = "Couldn't instantiate object of type " + type!!.name
            throw RuntimeException(error, e)
        }

        return event
    }
}
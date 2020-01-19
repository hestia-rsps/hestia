package worlds.gregs.hestia.artemis.event

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

    override fun dispatch(event: Event?) {
        if(event is InstantEvent) {
            super.dispatch(event)
        } else if(event != null) {
            eventQueue.add(event)
        }
    }
}
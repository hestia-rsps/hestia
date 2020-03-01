package worlds.gregs.hestia.core.script.dsl.artemis

import com.artemis.*
import net.mostlyoriginal.api.event.common.Event
import worlds.gregs.hestia.artemis.event.ExtendedEventListener
import kotlin.reflect.KClass

/**
 * Annotation to mark dsl classes for artemis [BaseSystem] [EntitySubscription] and [Event] listeners
 */
@DslMarker
annotation class ArtemisDsl

/**
 * Builder for creating [event] listeners
 * @param event The event to listen out for
 * @param priority The priority of the listener
 * @param skipCancelledEvents Whether the listener should be skipped if the event has been cancelled
 * @param conditional Any conditions that must be met before invoking
 * @param action The action to take if the conditions are met
 */
@ArtemisDsl
class EventListenerBuilder<E : Event>(private var event: KClass<E>, var priority: Int, var skipCancelledEvents: Boolean, var conditional: (E.() -> Boolean)?, var action: (E.() -> Unit)?) {
    /**
     * Alternative way of setting event condition
     * @param action The conditions to meet before invoking event action
     */
    fun where(action: E.() -> Boolean) {
        if (conditional != null) {
            throw IllegalArgumentException("Conditional has already been set for this listener $this")
        }
        this.conditional = action
    }


    /**
     * Alternative way of setting event action
     * @param action The action to take when an event is dispatched
     */
    fun then(action: E.() -> Unit) {
        if (this.action != null) {
            throw IllegalArgumentException("Action has already been set for this listener $this")
        }
        this.action = action
    }

    /**
     * @return the created [ExtendedEventListener] as long as [action] is set
     */
    @Suppress("UNCHECKED_CAST")
    fun build(): ExtendedEventListener? {
        return if (action != null) {
            ExtendedEventListener(event, priority, skipCancelledEvents, conditional as (Event.() -> Boolean)?, action!! as (Event.() -> Unit))
        } else {
            null
        }
    }
}
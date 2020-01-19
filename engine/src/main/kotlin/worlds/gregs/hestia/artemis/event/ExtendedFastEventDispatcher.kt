package worlds.gregs.hestia.artemis.event

import com.artemis.utils.Bag
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventDispatchStrategy
import net.mostlyoriginal.api.event.common.EventListener
import net.mostlyoriginal.api.event.dispatcher.FastEventDispatcher
import net.mostlyoriginal.api.utils.BagUtils
import net.mostlyoriginal.api.utils.ClassHierarchy
import java.util.*

/**
 * Duplicate of [FastEventDispatcher] but implementing [ComparableEvent] to join
 * [EventListener] and [ExtendedEventListener] into one sortable list
 */
open class ExtendedFastEventDispatcher : EventDispatchStrategy, ExtendedEventDispatchStrategy {

    val classHierarchy = ClassHierarchy()

    /** Listeners of exact event class. Excludes superclasses.  */
    val listenerCache = IdentityHashMap<Class<*>, Bag<ComparableEvent>>()

    /** Listeners flattened to include full hierarchy per calling event.  */
    val hierarchicalListenerCache = IdentityHashMap<Class<*>, Bag<ComparableEvent>?>()

    override fun register(listener: ExtendedEventListener) {
        // Bind listener to the related event class.
        val listenersFor = getListenersFor(listener.event.java, true)

        if (!listenersFor!!.any { it.event == listener }) {
            listenersFor.add(ComparableEvent(listener.priority, listener))
            // the hierarchical cache is now out of date. purrrrrrrrge!
            invalidateHierarchicalCache()
        }
    }

    override fun register(listener: EventListener?) {
        if (listener == null) throw NullPointerException("Listener required.")
        // Bind listener to the related event class.
        val listenersFor = getListenersFor(listener.parameterType, true)

        if (!listenersFor!!.any { it.event == listener }) {
            listenersFor.add(ComparableEvent(listener.priority, listener))
            // the hierarchical cache is now out of date. purrrrrrrrge!
            invalidateHierarchicalCache()
        }
    }

    private fun invalidateHierarchicalCache() {
        if (hierarchicalListenerCache.size > 0) {
            hierarchicalListenerCache.clear()
        }
    }

    /**
     * Get listeners for class (non hierarical).
     *
     * @param aClass Class to fetch listeners for.
     * @param createIfMissing instance empty bag when not exist.
     * @return Listener, or `null` if missing and not allowed to create.
     */
    protected open fun getListenersFor(aClass: Class<*>, createIfMissing: Boolean): Bag<ComparableEvent>? {
        var listeners = listenerCache[aClass]
        if (listeners == null && createIfMissing) { // if listener is missing, prep an empty bag.
            listeners = Bag(4)
            listenerCache[aClass] = listeners
        }
        return listeners
    }

    /**
     * Get listeners for class, including all superclasses.
     * Backed by cache.
     *
     * Not sorted!
     *
     * @param aClass Class to fetch listeners for.
     * @return Bag of listeners, empty if none found.
     */
    protected open fun getListenersForHierarchical(aClass: Class<*>): Bag<ComparableEvent>? {
        var listeners = hierarchicalListenerCache[aClass]
        if (listeners == null) {
            listeners = getListenersForHierarchicalUncached(aClass)
            // presort the listeners by priority.
            // Should speed things up in the case of an oft reused superclass.
            BagUtils.sort(listeners)
            hierarchicalListenerCache[aClass] = listeners
        }
        return listeners
    }

    /**
     * Get listeners for class, including all superclasses,
     * sorted by priority.
     *
     * Not backed by cache.
     *
     * @param aClass Class to fetch listeners for.
     * @return Bag of listeners, empty if none found.
     */
    private fun getListenersForHierarchicalUncached(aClass: Class<*>): Bag<ComparableEvent>? { // get hierarchy for event.
        val classes = classHierarchy.of(aClass)
        // step through hierarchy back to front, fetching the listeners for each step.
        val hierarchicalListeners = Bag<ComparableEvent>(4)
        for (c in classes) {
            val listeners = getListenersFor(c, false)
            if (listeners != null) {
                hierarchicalListeners.addAll(listeners)
            }
        }
        // sort by priority.
        BagUtils.sort(hierarchicalListeners)
        return hierarchicalListeners
    }

    /**
     * Dispatch event to registered listeners.
     * Events are called on the call stack, avoid deeply nested or circular event calls.
     */
    override fun dispatch(event: Event?) {
        if (event == null) throw NullPointerException("Event required.")
        /** Fetch hierarchical list of listeners.  */
        val listeners = getListenersForHierarchical(event.javaClass) ?: return

        var i = 0
        val s = listeners.size()
        while (i < s) {
            val listener = listeners[i].event
            if (listener is EventListener) {
                listener.handle(event)
            } else if (listener is ExtendedEventListener) {
                listener.handle(event)
            }
            i++
        }
    }

    override fun process() { // not interested in this stuff
    }

    override fun <T : Event?> dispatch(type: Class<T>?): T {
        throw UnsupportedOperationException("This dispatcher doesn't dispatch events by type!")
    }
}
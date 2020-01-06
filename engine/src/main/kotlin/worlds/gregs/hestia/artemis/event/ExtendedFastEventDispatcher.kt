package worlds.gregs.hestia.artemis.event

import com.artemis.utils.Bag
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventListener
import net.mostlyoriginal.api.event.dispatcher.FastEventDispatcher
import net.mostlyoriginal.api.utils.BagUtils
import worlds.gregs.hestia.artemis.dsl.ArtemisEventListener
import java.util.*
import kotlin.reflect.KClass

open class ExtendedFastEventDispatcher : FastEventDispatcher(), ExtendedEventDispatchStrategy {

    data class SortableThing(val priority: Int, val thing: Any) : Comparable<SortableThing> {
        override fun compareTo(other: SortableThing): Int {
            return other.priority - priority
        }
    }
    internal var listenersCache = IdentityHashMap<KClass<*>, Bag<ExtendedEventListener>>()

    var combinedListeners = IdentityHashMap<Class<*>, Bag<SortableThing>>()

    override fun register(eventListener: ArtemisEventListener) {
        val aClass = eventListener.event
        val listener = ExtendedEventListener(eventListener)
        val listenersFor = getListenersFor(aClass, true)
        if (listenersFor?.contains(listener) == false) {
            listenersFor.add(listener)
            //Sort listeners by priority
            BagUtils.sort(listenersFor)
        }
    }

    private fun getListenersFor(aClass: KClass<*>, createIfMissing: Boolean): Bag<ExtendedEventListener>? {
        var listeners: Bag<ExtendedEventListener>? = listenersCache[aClass]
        if (listeners == null && createIfMissing) {
            // if listener is missing, prep an empty bag.
            listeners = Bag(4)
            listenersCache[aClass] = listeners
        }
        return listeners
    }

    /**
     * Dispatch event to extended listeners.
     * Extended listeners are always called before regular ones, avoid mixing priorities.
     */
    override fun dispatch(event: Event) {
        if(!combinedListeners.containsKey(event::class.java)) {
            val hierarchy = getListenersForHierarchical(event::class.java)
            val normal = listenersCache[event::class]
            val bag = Bag<SortableThing>(hierarchy.size() + (normal?.size() ?: 0))
            hierarchy.forEach {
                bag.add(SortableThing(it.priority, it))
            }
            normal?.forEach {
                bag.add(SortableThing(it.priority, it))
            }
            BagUtils.sort(bag)
            combinedListeners[event::class.java] = bag
        }
        val listeners = combinedListeners[event::class.java]
        listeners?.forEach {
            val evnt = it.thing
            if(evnt is EventListener) {
                evnt.handle(event)
            } else if(evnt is ExtendedEventListener) {
                evnt.handle(event)
            }
        }
    }
}
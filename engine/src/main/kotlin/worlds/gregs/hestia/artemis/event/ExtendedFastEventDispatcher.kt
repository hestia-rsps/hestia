package worlds.gregs.hestia.artemis.event

import com.artemis.utils.Bag
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.dispatcher.FastEventDispatcher
import net.mostlyoriginal.api.utils.BagUtils
import worlds.gregs.hestia.artemis.dsl.ArtemisEventListener
import java.util.*
import kotlin.reflect.KClass

open class ExtendedFastEventDispatcher : FastEventDispatcher(), ExtendedEventDispatchStrategy {

    internal var listenersCache = IdentityHashMap<KClass<*>, Bag<ExtendedEventListener>>()

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
        val listeners = listenersCache[event::class]
        listeners?.forEach {
            it.handle(event)
        }
        super.dispatch(event)
    }
}
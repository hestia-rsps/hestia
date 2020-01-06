package worlds.gregs.hestia.core.script.dsl.artemis

import com.artemis.*
import com.artemis.systems.IteratingSystem
import com.artemis.utils.IntBag
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.dsl.ArtemisEventListener
import worlds.gregs.hestia.artemis.forEach
import worlds.gregs.hestia.core.script.dsl.artemis.SystemBuilder.Companion.EMPTY_ASPECT
import kotlin.reflect.KClass

/**
 * Annotation to mark dsl classes for artemis [BaseSystem] [EntitySubscription] and [Event] listeners
 */
@DslMarker
annotation class ArtemisDsl

data class ArtemisSystem(val priority: Int, val aspect: Aspect.Builder?, val subscription: ArtemisSubscription?, val initialize: (() -> Unit)?, val process: ((Int) -> Unit)?, val parallel: Boolean, val dispose: (() -> Unit)?) {
    fun build(): BaseSystem? {
        return when {
            aspect == null && subscription != null -> null
            aspect == null && (initialize != null || dispose != null) -> DslPassiveSystem(initialize, dispose)
            aspect == null -> PassiveSystem()
            parallel && process != null -> DslParallelSystem(aspect, initialize, dispose, process, subscription?.inserted, subscription?.removed)
            process != null -> DslIteratingSystem(aspect, initialize, dispose, process, subscription?.inserted, subscription?.removed)
            subscription != null -> DslSubscriptionSystem(aspect, initialize, dispose, subscription.inserted, subscription.removed)
            else -> null
        }
    }
}

private class Listener(private val inserted: ((Int) -> Unit)?, private val removed: ((Int) -> Unit)?) : EntitySubscription.SubscriptionListener {
    override fun inserted(entities: IntBag) {
        if(inserted != null) {
            entities.forEach {
                inserted.invoke(it)
            }
        }
    }

    override fun removed(entities: IntBag) {
        if(removed != null) {
            entities.forEach {
                removed.invoke(it)
            }
        }
    }
}

data class ArtemisSubscription(val aspect: Aspect.Builder?, val inserted: ((Int) -> Unit)?, val removed: ((Int) -> Unit)?) {

    fun listener(): EntitySubscription.SubscriptionListener {
        return Listener(inserted, removed)
    }

}

/**
 * Builder for creating [ArtemisSystem]s
 */
@ArtemisDsl
class SystemBuilder(private val subscriptions: MutableList<ArtemisSubscription>) : Aspects {

    /**
     * Subscription with the same type as [aspect]
     */
    private var subscription: ArtemisSubscription? = null

    /**
     * Processing [BaseEntitySystem]
     */
    private var process: ((entityId: Int) -> Unit)? = null

    /**
     * Called on system setup
     */
    private var initialize: (() -> Unit)? = null

    /**
     * Called on system disposal
     */
    private var dispose: (() -> Unit)? = null

    /**
     * The [aspect] of the system
     */
    val aspect: Aspect.Builder = Aspect.all()

    /**
     * The priority of the system
     */
    var priority = WorldConfigurationBuilder.Priority.NORMAL

    /**
     * Whether the system should be ran in parallel
     */
    var parallel = false

    /**
     * Function for creating entity subscriptions
     * @param aspect The aspect to subscribe to
     * @param insert The action to take when an entity is inserted
     * @param remove The action to take when an entity is removed
     * @param setup Alternative builder setup
     */
    fun subscribe(aspect: Aspect.Builder? = null, insert: ((entityId: Int) -> Unit)? = null, remove: ((entityId: Int) -> Unit)? = null, setup: SubscribeBuilder.() -> Unit = {}) {
        val builder = SubscribeBuilder(aspect, insert, remove)
        builder.setup()
        if (builder.aspect == EMPTY_ASPECT) {
            if (subscription != null) {
                throw IllegalArgumentException("Entity listener has already been set for this system $this")
            }
            subscription = builder.build()
        } else {
            subscriptions += builder.build()
        }
    }

    /**
     * Function for setting processing for [IteratingSystem]s
     */
    fun process(action: (entityId: Int) -> Unit) {
        if (process != null) {
            throw IllegalArgumentException("Processing has already been set for this system $this")
        }
        process = action
    }

    /**
     * Function for setting processing for [IteratingSystem]s
     */
    fun initialize(action: () -> Unit) {
        if (initialize != null) {
            throw IllegalArgumentException("Initialize has already been set for this system $this")
        }
        initialize = action
    }

    fun dispose(action: () -> Unit) {
        if (dispose != null) {
            throw IllegalArgumentException("Dispose has already been set for this system $this")
        }
        dispose = action
    }

    /**
     * Creates the system
     */
    fun build(): ArtemisSystem {
        return ArtemisSystem(priority, if (aspect == EMPTY_ASPECT) null else aspect, subscription, initialize, process, parallel, dispose)
    }

    /**
     * Function to prevent systems from being nested
     */
    @Suppress("UNUSED_PARAMETER")
    @Deprecated(level = DeprecationLevel.ERROR, message = "Systems can't be nested.")
    fun system(param: () -> Unit = {}) {
    }

    companion object {
        val EMPTY_ASPECT: Aspect.Builder = Aspect.all()
    }
}

/**
 * Builder for creating entity subscriptions
 * @param aspect The aspect to subscribe to
 * @param insert The action to take when an entity is inserted
 * @param remove The action to take when an entity is removed
 */
@ArtemisDsl
class SubscribeBuilder(aspect: Aspect.Builder?, var insert: ((Int) -> Unit)?, var remove: ((Int) -> Unit)?) : Aspects {
    //Open and non-null to allow for infix changes
    val aspect: Aspect.Builder = aspect ?: Aspect.all()

    /**
     * Alternative way of setting insert action
     * @param action The action to take when an entity is inserted
     */
    fun insert(action: (entityId: Int) -> Unit) {
        if (this.insert != null) {
            throw IllegalArgumentException("Insert action has already been set for this subscription $this")
        }
        this.insert = action
    }

    /**
     * Alternative way of setting remove action
     * @param action The action to take when an entity is removed
     */
    fun remove(action: (entityId: Int) -> Unit) {
        if (this.remove != null) {
            throw IllegalArgumentException("Remove action has already been set for this subscription $this")
        }
        this.remove = action
    }

    /**
     * @return the created [ArtemisSubscription]
     */
    fun build(): ArtemisSubscription {
        return ArtemisSubscription(if (aspect == EMPTY_ASPECT) null else aspect, insert, remove)
    }
}

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
     * @return the created [ArtemisSubscription] as long as [action] is set
     */
    @Suppress("UNCHECKED_CAST")
    fun build(): ArtemisEventListener? {
        return if (action != null) {
            ArtemisEventListener(event, priority, skipCancelledEvents, conditional as (Event.() -> Boolean)?, action!! as (Event.() -> Unit))
        } else {
            null
        }
    }
}
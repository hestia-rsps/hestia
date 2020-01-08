package worlds.gregs.hestia.core.script

import com.artemis.Aspect
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.annotations.Wire
import worlds.gregs.hestia.artemis.event.ExtendedEventDispatchStrategy
import worlds.gregs.hestia.artemis.event.ExtendedEventListener
import worlds.gregs.hestia.core.action.WorldEvent
import worlds.gregs.hestia.core.script.dsl.artemis.*
import kotlin.script.experimental.annotations.KotlinScript

@KotlinScript(displayName = "Hestia Script", fileExtension = "script.kts", compilationConfiguration = ScriptConfiguration::class)
@Wire(failOnNull = false, injectInherited = true)
abstract class ScriptBase : ScriptBuilder() {

    val systems = mutableListOf<ArtemisSystem>()
    /**
     * List of entity subscriptions
     */
    val subscriptions = mutableListOf<ArtemisSubscription>()
    /**
     * List of entity event listeners
     */
    val listeners = mutableListOf<ExtendedEventListener>()

    /**
     * Alternative as [world] returns [NoSuchMethodError] if outside of a system
     */
    lateinit var game: World

    /**
     * Function for creating event listeners
     * @param conditional Any conditions that must be met before the listener can be invoked
     * @param action The action to take when conditions are met
     * @param setup Alternative builder setup
     */
    inline fun <reified E : WorldEvent> on(priority: Int = 0, skipCancelledEvents: Boolean = true, noinline conditional: (E.() -> Boolean)? = null, noinline action: (E.() -> Unit)? = null, setup: EventListenerBuilder<E>.() -> Unit = {}) {
        val builder = EventListenerBuilder(E::class, priority, skipCancelledEvents, conditional, action)
        builder.setup()
        listeners += builder.build() ?: return
    }

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
        if (builder.aspect == SystemBuilder.EMPTY_ASPECT) {
            throw IllegalArgumentException("Subscription must have an entity aspect $this")
        } else {
            subscriptions += builder.build()
        }
    }

    @ArtemisDsl
    fun system(setup: SystemBuilder.() -> Unit): ArtemisSystem {
        val systemBuilder = SystemBuilder(subscriptions)
        systemBuilder.setup()
        val system = systemBuilder.build()
        systems += system
        return system
    }

    override fun build(builder: WorldConfigurationBuilder) {
        builder.with(this)
        systems.forEach {
            builder.with(it.priority, it.build() ?: return@forEach)
        }
    }

    override fun build(world: World, dispatcher: ExtendedEventDispatchStrategy) {
        val subscriptionManager = world.aspectSubscriptionManager
        subscriptions.forEach {
            subscriptionManager.get(it.aspect).addSubscriptionListener(it.listener())
        }
        listeners.forEach {
            dispatcher.register(it)
        }
    }
}
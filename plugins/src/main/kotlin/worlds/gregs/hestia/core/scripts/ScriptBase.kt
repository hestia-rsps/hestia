package worlds.gregs.hestia.core.scripts

import com.artemis.Aspect
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Event
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.artemis.dsl.ArtemisEventListener
import worlds.gregs.hestia.artemis.event.ExtendedEventDispatchStrategy
import worlds.gregs.hestia.core.plugins.dialogue.components.DialogueQueue
import worlds.gregs.hestia.core.scripts.dsl.*
import worlds.gregs.hestia.game.queue.QueueContext
import worlds.gregs.hestia.game.queue.QueueScope
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
    val listeners = mutableListOf<ArtemisEventListener>()
    /**
     * List of dialogue coroutines
     */
    val dialogues = mutableMapOf<String, DialogueQueue>()

    internal var dialogueBase: DialogueBase? = null

    /**
     * Function for creating event listeners
     * @param conditional Any conditions that must be met before the listener can be invoked
     * @param action The action to take when conditions are met
     * @param setup Alternative builder setup
     */
    inline fun <reified E : Event> on(priority: Int = 0, skipCancelledEvents: Boolean = false, noinline conditional: ((E) -> Boolean)? = null, noinline action: ((event: Event) -> Unit)? = null, setup: EventListenerBuilder<E>.() -> Unit = {}) {
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
        dialogues.forEach { (name, block) ->
            dialogueBase?.addDialogue(name, block)
        }
    }

    fun dialogue(name: String, action: DialogueQueue): String {
        dialogues[name] = action
        return name
    }

    fun dialogue(action: DialogueQueue) = action

    fun <T: QueueContext> queue(action: suspend QueueScope<T>.() -> Unit) = action
}
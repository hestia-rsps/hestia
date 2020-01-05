package worlds.gregs.hestia.core.script

import com.artemis.Aspect
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.dsl.ArtemisEventListener
import worlds.gregs.hestia.artemis.event.ExtendedEventDispatchStrategy
import worlds.gregs.hestia.core.display.dialogue.api.DialogueBase
import worlds.gregs.hestia.core.script.dsl.artemis.*
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskPriority
import worlds.gregs.hestia.core.task.api.closeDialogue
import worlds.gregs.hestia.core.task.api.event.EntityEvent
import worlds.gregs.hestia.core.task.api.event.TargetEvent
import worlds.gregs.hestia.core.task.logic.systems.awaitScreen
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.ReusableTask
import worlds.gregs.hestia.core.task.model.TaskContinuation
import worlds.gregs.hestia.core.task.model.context.EntityContext
import worlds.gregs.hestia.core.task.model.context.ParamContext
import worlds.gregs.hestia.core.task.model.events.StartTask
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
    val dialogues = mutableMapOf<String, ReusableTask>()

    internal var dialogueBase: DialogueBase? = null

    internal var eventSystem: EventSystem? = null

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
    inline fun <reified E : Event> on(priority: Int = 0, skipCancelledEvents: Boolean = true, noinline conditional: (E.(E) -> Boolean)? = null, noinline action: (Event.(event: Event) -> Unit)? = null, setup: EventListenerBuilder<E>.() -> Unit = {}) {
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
        dialogues.forEach { (name, task) ->
            dialogueBase?.addDialogue(name, task)
        }
    }

    fun dialogue(name: String, priority: TaskPriority = TaskPriority.Normal, action: SuspendableQueue): String {
        check(!dialogues.containsKey(name)) { "Dialogue '$name' already exists." }
        dialogues[name] = ReusableTask(priority) {
            onCancel {
                closeDialogue()
            }

            if(priority == TaskPriority.Low) {
                awaitScreen()
            }
            action()
            closeDialogue()
        }
        return name
    }

    private fun extension(action: SuspendableQueue) = action

    /**
     * If [priority] is [TaskPriority.Low] then wait for screen to close before continuing
     * @param priority The rule to start the task
     * @param action The suspendable queue to process
     */
    fun queue(priority: TaskPriority = TaskPriority.Normal, action: SuspendableQueue): SuspendableQueue = if (priority == TaskPriority.Low) extension { awaitScreen(); action() } else action

    fun task(entityId: Int, priority: TaskPriority = TaskPriority.Normal, action: SuspendableQueue) =
            task(entityId, priority, Unit, action)

    fun <T : Any> task(entityId: Int, priority: TaskPriority = TaskPriority.Normal, param: T, action: SuspendableQueue) {
        eventSystem?.dispatch(StartTask(entityId, InactiveTask(ReusableTask(priority, queue(priority, action)), param)))
    }

    fun <E : EntityEvent> EventListenerBuilder<E>.task(priority: TaskPriority = TaskPriority.Normal, action: SuspendableQueue) : EventListenerBuilder<E> {
        then {
            task(it.entity, priority, it, action)
        }
        return this
    }

    fun <E : EntityEvent> EventListenerBuilder<E>.whereTask(action: Task.(E) -> Boolean) : EventListenerBuilder<E> {
        where {
            //LOL HAx
            action(TaskContinuation(if(this is TargetEvent) ParamContext(EntityContext(game, it.entity), this.target) else EntityContext(game, it.entity)), this)
        }
        return this
    }
}
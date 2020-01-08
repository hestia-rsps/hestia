package worlds.gregs.hestia.core.action

import com.artemis.BaseSystem
import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.World
import net.mostlyoriginal.api.event.common.Event
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.artemis.events.OutBoundMessage
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.TaskPriority
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.ReusableTask
import worlds.gregs.hestia.core.task.model.events.StartTask
import kotlin.reflect.KClass

abstract class WorldEvent : Event {
    lateinit var world: World

    infix fun Int.perform(action: Action) {
        action.world = world
        action.entity = this
        dispatch(action)
    }

    infix fun Int.send(message: Message) = dispatch(OutBoundMessage(this, message))

    fun dispatch(event: Event) = world.dispatch(event)

    infix fun <T : Component> Int.get(c: KClass<T>): T = map(c).get(this)

    infix fun <T : Component> Int.create(c: KClass<T>): T = map(c).create(this)

    infix fun <T : Component> Int.remove(c: KClass<T>) = map(c).remove(this)

    infix fun <T : Component> Int.has(c: KClass<T>) = map(c).has(this)

    fun <T : Component> map(c: KClass<T>): ComponentMapper<T> = world.getMapper(c.java)

    infix fun <T : Component> World.map(c: KClass<T>): ComponentMapper<T> = getMapper(c.java)

    fun <T : BaseSystem> system(c: KClass<T>): T = world.getSystem(c.java)

    infix fun <T : BaseSystem> World.system(c: KClass<T>): T = getSystem(c.java)


    fun task(priority: TaskPriority = TaskPriority.Normal, action: SuspendableQueue) : Action {
        return StartTask(InactiveTask(ReusableTask(priority, action), Unit))
    }


    fun log(message: String) = logger.warn(message)

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)!!
    }
}
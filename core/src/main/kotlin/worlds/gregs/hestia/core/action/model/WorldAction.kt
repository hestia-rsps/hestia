package worlds.gregs.hestia.core.action.model

import com.artemis.BaseSystem
import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.World
import net.mostlyoriginal.api.event.common.Event
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.artemis.events.OutBoundMessage
import worlds.gregs.hestia.core.action.logic.dispatch
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.TaskPriority
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.ReusableTask
import worlds.gregs.hestia.core.task.model.events.StartTask
import kotlin.reflect.KClass

abstract class WorldAction : Action {
    override lateinit var world: World

    override fun perform(action: Action) {
        action.world = world
        dispatch(action)
    }

    override infix fun Int.send(message: Message) = dispatch(OutBoundMessage(this, message))

    override fun dispatch(event: Event) = world.dispatch(event)

    override infix fun <T : Component> Int.get(c: KClass<T>): T = map(c).get(this)

    override infix fun <T : Component> Int.getUnsafe(c: KClass<T>): T? = map(c).get(this)

    override infix fun <T : Component> Int.create(c: KClass<T>): T = map(c).create(this)

    override infix fun <T : Component> Int.remove(c: KClass<T>) = map(c).remove(this)

    override infix fun <T : Component> Int.has(c: KClass<T>) = map(c).has(this)

    override fun <T : Component> map(c: KClass<T>): ComponentMapper<T> = world.getMapper(c.java)

    override infix fun <T : Component> World.map(c: KClass<T>): ComponentMapper<T> = getMapper(c.java)

    override fun <T : BaseSystem> system(c: KClass<T>): T = world.getSystem(c.java)

    override infix fun <T : BaseSystem> World.system(c: KClass<T>): T = getSystem(c.java)

    override fun task(priority: TaskPriority, action: SuspendableQueue): EntityAction {
        return StartTask(InactiveTask(ReusableTask(priority, action), Unit))
    }

    override fun log(message: String) = logger.warn(message)

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)!!
    }
}
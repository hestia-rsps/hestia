package worlds.gregs.hestia.core.action.model

import com.artemis.BaseSystem
import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.World
import net.mostlyoriginal.api.event.common.Event
import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.TaskPriority
import kotlin.reflect.KClass

interface Action : Event {
    var world: World

    fun perform(action: Action)

    infix fun Int.send(message: Message)

    fun dispatch(event: Event)

    infix fun <T : Component> Int.get(c: KClass<T>): T

    infix fun <T : Component> Int.create(c: KClass<T>): T

    infix fun <T : Component> Int.remove(c: KClass<T>)

    infix fun <T : Component> Int.has(c: KClass<T>): Boolean

    fun <T : Component> map(c: KClass<T>): ComponentMapper<T>

    infix fun <T : Component> World.map(c: KClass<T>): ComponentMapper<T>

    fun <T : BaseSystem> system(c: KClass<T>): T

    infix fun <T : BaseSystem> World.system(c: KClass<T>): T

    fun task(priority: TaskPriority = TaskPriority.Normal, action: SuspendableQueue) : EntityAction

    fun log(message: String)

}
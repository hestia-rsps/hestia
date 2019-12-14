package worlds.gregs.hestia.core.task.model.context

import com.artemis.World
import worlds.gregs.hestia.core.script.dsl.artemis.EventListenerBuilder
import worlds.gregs.hestia.core.task.api.EntityTask
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskContext
import worlds.gregs.hestia.core.task.api.event.EntityEvent

data class ParamContext<T>(override val world: World, override val entity: Int, val param: T) : TaskContext(), EntityTask {
    constructor(entityContext: EntityContext, param: T) : this(entityContext.world, entityContext.entity, param)
}

fun <T> Task.getParam(): T {
    return (context as ParamContext<T>).param
}

fun <E : EntityEvent> EventListenerBuilder<E>.event(task: Task, block: E.() -> Unit) {
    with(task.getParam(), block)
}
fun <E : EntityEvent> EventListenerBuilder<E>.event(task: Task) = task.getParam<E>()

package worlds.gregs.hestia.core.task.api.event

import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.model.context.getParam

interface TargetEvent : EntityEvent {
    val target: Int
}

val Task.target: Int
    get() = getParam<TargetEvent>().target
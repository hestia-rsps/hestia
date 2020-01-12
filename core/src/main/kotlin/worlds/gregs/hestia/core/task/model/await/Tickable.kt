package worlds.gregs.hestia.core.task.model.await

import worlds.gregs.hestia.core.task.api.TaskType

interface Tickable : TaskType<Unit> {
    var ticks: Int
}
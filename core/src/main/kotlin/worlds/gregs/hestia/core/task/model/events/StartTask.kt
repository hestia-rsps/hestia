package worlds.gregs.hestia.core.task.model.events

import worlds.gregs.hestia.core.task.model.Task
import worlds.gregs.hestia.artemis.InstantEvent

/**
 * Queue's a priority task
 */
data class StartTask(val entityId: Int, val task: Task) : InstantEvent
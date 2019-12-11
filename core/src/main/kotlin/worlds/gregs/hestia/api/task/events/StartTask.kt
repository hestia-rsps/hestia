package worlds.gregs.hestia.api.task.events

import worlds.gregs.hestia.api.task.Task
import worlds.gregs.hestia.artemis.InstantEvent

/**
 * Queue's a priority task
 */
data class StartTask(val entityId: Int, val task: Task) : InstantEvent
package worlds.gregs.hestia.core.task.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.model.InactiveTask

/**
 * Queue's a priority task
 */
data class StartTask(val task: InactiveTask<*>) : EntityAction(), InstantEvent
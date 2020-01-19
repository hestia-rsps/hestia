package worlds.gregs.hestia.core.task.model.events

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.TaskSuspension

/**
 * Event for systems to handle a [TaskSuspension] if it's applicable to them.
 */
data class ProcessTaskSuspension(val type: TaskSuspension<*>) : EntityAction()
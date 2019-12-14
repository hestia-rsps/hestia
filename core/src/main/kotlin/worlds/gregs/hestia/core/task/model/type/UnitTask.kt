package worlds.gregs.hestia.core.task.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskType

data class UnitTask(override val continuation: CancellableContinuation<Unit>) : TaskType<Unit>
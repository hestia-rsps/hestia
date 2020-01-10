package worlds.gregs.hestia.core.task.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskType

data class UnitTask(override var continuation: CancellableContinuation<Unit>) : TaskType<Unit>
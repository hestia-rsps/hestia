package worlds.gregs.hestia.core.task.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.TaskType

data class StringData(override val continuation: CancellableContinuation<String>) : TaskType<String>
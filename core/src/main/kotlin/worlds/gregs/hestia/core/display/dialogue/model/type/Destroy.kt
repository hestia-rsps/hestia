package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.TaskType

data class Destroy(val text: String, val item: Int) : EntityAction(), TaskType<Boolean> {
    override lateinit var continuation: CancellableContinuation<Boolean>
}
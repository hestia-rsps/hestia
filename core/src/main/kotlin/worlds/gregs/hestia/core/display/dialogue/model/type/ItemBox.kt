package worlds.gregs.hestia.core.display.dialogue.model.type

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.model.await.Resendable

data class ItemBox(val lines: String, val model: Int, val zoom: Int) : EntityAction(), TaskType<Unit>, Resendable {
    override lateinit var continuation: CancellableContinuation<Unit>
}
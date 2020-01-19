package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.task.api.Tickable

data class Ticks(override var ticks: Int) : Tickable {
    override lateinit var continuation: CancellableContinuation<Unit>
}
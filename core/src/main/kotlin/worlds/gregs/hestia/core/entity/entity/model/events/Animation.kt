package worlds.gregs.hestia.core.entity.entity.model.events

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.task.model.await.Tickable

data class Animation(val id: Int, val speed: Int = 0): EntityAction(), Tickable, InstantEvent {
    override var ticks: Int = 0
    override lateinit var continuation: CancellableContinuation<Unit>
}
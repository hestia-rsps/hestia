package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.model.await.ClearTasks
import worlds.gregs.hestia.core.task.model.await.Route
import worlds.gregs.hestia.core.task.model.await.RouteResult
import worlds.gregs.hestia.core.task.model.await.Ticks
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.core.world.movement.model.events.Interact

interface InteractionHandler {

    var stepsMapper: ComponentMapper<Steps>
    var followingMapper: ComponentMapper<Following>
    var shiftMapper: ComponentMapper<Shift>
    var es: EventSystem

    fun clear(entityId: Int) {
        //Clear current steps
        stepsMapper.get(entityId)?.clear()

        //Clear follow
        followingMapper.remove(entityId)

        //Clear any movement
        shiftMapper.remove(entityId)
    }

    fun interact(entityId: Int, target: Int, wait: Boolean, completion: () -> Unit): SuspendableQueue = {
        onCancel {
            if (it is TaskCancellation.OutOfReach) {
                es.perform(entityId, Chat("You can't reach that."))
            }
            clear(entityId)
        }
        suspendCancellableCoroutine<Unit> {
            val clear = ClearTasks(1, TaskCancellation.Priority)
            clear.continuation = it
            suspension = clear
            es.perform(entityId, clear)
        }
        suspendCancellableCoroutine<RouteResult> {
            val clear = Route()
            clear.continuation = it
            suspension = clear
            es.perform(entityId, Interact(target))
        }
        clear(entityId)
        if (wait) {
            suspendCancellableCoroutine<Unit> {
                val tick = Ticks(1)
                tick.continuation = it
                suspension = tick
            }
        }

        completion()
    }
}
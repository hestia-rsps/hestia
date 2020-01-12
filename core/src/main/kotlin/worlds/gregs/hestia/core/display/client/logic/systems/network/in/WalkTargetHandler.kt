package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.await.ClearTasks
import worlds.gregs.hestia.core.task.model.events.StartTask
import worlds.gregs.hestia.core.world.movement.logic.strategies.FixedTileStrategy
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.core.world.movement.model.components.calc.Path
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.WalkMap

class WalkTargetHandler : MessageHandlerSystem<WalkMap>() {
    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var followingMapper: ComponentMapper<Following>
    private lateinit var shiftMapper: ComponentMapper<Shift>

    private lateinit var es: EventSystem

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WalkMap) {
        val (x, y, running) = message
        es.perform(entityId, StartTask(InactiveTask(1) {
            suspendCancellableCoroutine<Unit> {
                val clear = ClearTasks(1, TaskCancellation.Walk(x, y))
                clear.continuation = it
                suspension = clear
                es.perform(entityId, clear)
            }

            //TODO temp clearing, needs a proper system
            //Clear current steps
            stepsMapper.get(entityId)?.clear()

            //Clear follow
            followingMapper.remove(entityId)

            //Clear any movement
            shiftMapper.remove(entityId)

            //Calculate path
            pathMapper.create(entityId).apply {
                this.strategy = FixedTileStrategy(x, y)
            }
        }))
    }

}
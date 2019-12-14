package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.world.movement.logic.strategies.FixedTileStrategy
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.calc.Follow
import worlds.gregs.hestia.core.world.movement.model.components.calc.Path
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.WalkMap

class WalkTargetHandler : MessageHandlerSystem<WalkMap>() {
    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var followMapper: ComponentMapper<Follow>
    private lateinit var shiftMapper: ComponentMapper<Shift>

    private lateinit var tasks: Tasks

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WalkMap) {
        val (x, y, running) = message

        //TODO temp clearing, needs a proper system
        tasks.cancelAll(entityId, TaskCancellation.Walk)
        //Clear current steps
        stepsMapper.get(entityId)?.clear()

        //Clear follow
        followMapper.remove(entityId)

        //Clear any movement
        shiftMapper.remove(entityId)

        //Calculate path
        pathMapper.create(entityId).apply {
            this.strategy = FixedTileStrategy(x, y)
        }
    }

}
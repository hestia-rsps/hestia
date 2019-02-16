package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.ComponentMapper
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.game.MessageHandlerSystem
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.plugins.movement.components.calc.Follow
import worlds.gregs.hestia.game.plugins.movement.components.calc.Path
import worlds.gregs.hestia.game.plugins.movement.strategies.FixedTileStrategy
import worlds.gregs.hestia.network.game.decoders.messages.WalkTarget

class WalkTargetHandler : MessageHandlerSystem<WalkTarget>() {
    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var followMapper: ComponentMapper<Follow>
    private lateinit var shiftMapper: ComponentMapper<Shift>

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WalkTarget) {
        val (baseX, baseY, route, running) = message

        //TODO temp clearing, needs a proper system
        //Clear current steps
        stepsMapper.get(entityId)?.clear()

        //Clear follow
        followMapper.remove(entityId)

        //Clear any movement
        shiftMapper.remove(entityId)

        //TODO instead of route re-calculation, just validate each step and direction
        //Calculate path
        pathMapper.create(entityId).apply {
            this.strategy = FixedTileStrategy(baseX + route.last().first, baseY + route.last().second)
        }
    }

}
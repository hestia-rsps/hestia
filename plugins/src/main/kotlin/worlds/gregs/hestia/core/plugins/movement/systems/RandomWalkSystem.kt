package worlds.gregs.hestia.core.plugins.movement.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IntervalIteratingSystem
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.core.plugins.movement.components.RandomWalk
import worlds.gregs.hestia.core.plugins.movement.components.Steps
import worlds.gregs.hestia.core.plugins.movement.components.calc.Follow
import worlds.gregs.hestia.core.plugins.movement.components.calc.Path
import worlds.gregs.hestia.core.plugins.movement.strategies.FixedTileStrategy
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.nearby

class RandomWalkSystem : IntervalIteratingSystem(Aspect.all(Position::class, Mobile::class, RandomWalk::class), 2.0F) {//3 seconds

    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var followMapper: ComponentMapper<Follow>
    private lateinit var shiftMapper: ComponentMapper<Shift>

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        //Clear current steps
        stepsMapper.get(entityId)?.clear()

        //Clear follow
        followMapper.remove(entityId)

        //Clear any movement
        shiftMapper.remove(entityId)

        //Set a path
        pathMapper.create(entityId).apply {
            this.strategy = FixedTileStrategy(position.x.nearby(2).random(), position.y.nearby(2).random())
        }
    }

}
package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.toArray
import worlds.gregs.hestia.core.display.update.logic.DirectionUtils.Companion.getOffset
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.model.MovementType
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.core.world.movement.model.components.calc.Step
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement

@Wire(failOnNull = false)
class FollowSystem : IteratingSystem(Aspect.all(Position::class, Shift::class)) {
    private lateinit var followingMapper: ComponentMapper<Following>
    private lateinit var movementMapper: ComponentMapper<Movement>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var stepMapper: ComponentMapper<Step>
    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var followers: EntitySubscription

    override fun initialize() {
        super.initialize()
        followers = world.aspectSubscriptionManager.get(Aspect.all(Following::class))
    }

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)

        //Get all followers
        val entities = followers.entities.toArray().filter { followingMapper.get(it).entity == entityId }

        val shift = shiftMapper.get(entityId)

        //Cancel follow if target changes plane or instant moves
        if(shift.plane != 0 || movementMapper.get(entityId).actual == MovementType.Move) {
            entities.forEach {
                followingMapper.remove(it)
            }
            return
        }

        //Position without shift, but applying and extra offset from moving multiple tiles (running)
        val targetX = position.x + (shift.x - getOffset(0, shift.x))
        val targetY = position.y + (shift.y - getOffset(0, shift.y))
        //Move to last position
        entities.forEach {
            stepMapper.create(it).apply {
                x = targetX
                y = targetY
                //We're assuming that their last position was a valid one. So no check.
            }
        }
    }
}
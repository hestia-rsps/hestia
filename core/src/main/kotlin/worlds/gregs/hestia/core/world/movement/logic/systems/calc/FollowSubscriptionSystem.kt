package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.update.model.components.direction.Watch
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem.Companion.withinContact
import worlds.gregs.hestia.core.world.movement.model.components.calc.Beside
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.core.world.movement.model.events.Follow

/**
 * FollowSubscriptionSystem
 * Adds/removes watching the entity being followed when [Following] is inserted/removed
 */
class FollowSubscriptionSystem : SubscriptionSystem(Aspect.all(Position::class, Following::class)) {
    private lateinit var followingMapper: ComponentMapper<Following>
    private lateinit var besideMapper: ComponentMapper<Beside>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var sizeMapper: ComponentMapper<Size>
    private lateinit var mobileMapper: ComponentMapper<Mobile>
    private lateinit var es: EventSystem

    @Subscribe
    fun follow(action: Follow) {
        val entityId = action.entity
        if(entityId == -1) {
            followingMapper.remove(entityId)
        } else if(mobileMapper.has(entityId)) {
            val following = followingMapper.create(entityId)
            following.entity = action.target
        }
    }

    override fun inserted(entityId: Int) {
        //Watch
        val follow = followingMapper.get(entityId)
        es.perform(entityId, Watch(follow.entity))

        if(follow.entity <= -1) {
            return
        }

        val position = positionMapper.get(entityId)
        val targetPosition = positionMapper.get(follow.entity)

        val width = sizeMapper.width(follow.entity)
        val height = sizeMapper.height(follow.entity)

        if(withinContact(position.x, position.y, targetPosition.x, targetPosition.y, width, height)) {
            return//TODO what about walls?
        }

        //Walk beside (if not already)
        besideMapper.create(entityId).apply {
            this.x = targetPosition.x
            this.y = targetPosition.y
            this.sizeX = width
            this.sizeY = height
            this.calculate = true
            this.beside = true
        }
    }

    override fun removed(entityId: Int) {
        es.perform(entityId, Watch(-1))
    }
}
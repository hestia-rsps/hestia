package worlds.gregs.hestia.game.systems.movement

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.component.movement.Mobile
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.services.Aspect

/**
 * MobileSystem
 * Handles entities which are mobile
 */
class MobileSystem : IteratingSystem(Aspect.all(Position::class, Mobile::class)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var mobileMapper: ComponentMapper<Mobile>

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        val mobile = mobileMapper.get(entityId)
        mobile.lastX = position.x
        mobile.lastY = position.y
        mobile.lastPlane = position.plane
    }
}
package worlds.gregs.hestia.core.world.movement.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.artemis.Aspect

class PostMovementSystem : IteratingSystem(Aspect.all(Mobile::class)) {

    private lateinit var shiftMapper: ComponentMapper<Shift>

    override fun process(entityId: Int) {
        shiftMapper.remove(entityId)
    }

}
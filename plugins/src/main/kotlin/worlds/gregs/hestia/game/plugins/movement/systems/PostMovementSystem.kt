package worlds.gregs.hestia.game.plugins.movement.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.services.Aspect

class PostMovementSystem : IteratingSystem(Aspect.all(Mobile::class)) {

    private lateinit var shiftMapper: ComponentMapper<Shift>

    override fun process(entityId: Int) {
        shiftMapper.remove(entityId)
    }

}
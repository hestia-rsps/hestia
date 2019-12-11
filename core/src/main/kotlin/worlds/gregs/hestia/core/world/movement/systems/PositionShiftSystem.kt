package worlds.gregs.hestia.core.world.movement.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.services.Aspect

class PositionShiftSystem : IteratingSystem(Aspect.all(Position::class, Shift::class)) {
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var shiftMapper: ComponentMapper<Shift>

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        val shift = shiftMapper.get(entityId)
        position.x += shift.x
        position.y += shift.y
        position.plane += shift.plane
    }
}
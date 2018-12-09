package worlds.gregs.hestia.game.plugins.movement.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.api.movement.Shift
import worlds.gregs.hestia.game.plugins.core.components.map.Position
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
        shiftMapper.remove(entityId)
    }
}
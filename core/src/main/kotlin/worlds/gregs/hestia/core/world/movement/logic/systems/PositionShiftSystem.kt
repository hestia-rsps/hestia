package worlds.gregs.hestia.core.world.movement.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.events.Moved

class PositionShiftSystem : IteratingSystem(Aspect.all(Position::class, Shift::class)) {
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var es: EventSystem

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        val shift = shiftMapper.get(entityId)
        position.x += shift.x
        position.y += shift.y
        position.plane += shift.plane
        es.dispatch(Moved(entityId))
    }
}
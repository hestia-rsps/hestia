package world.gregs.hestia.game.systems.movement

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.movement.ShiftPosition
import world.gregs.hestia.services.Aspect

class PositionShiftSystem : IteratingSystem(Aspect.all(Position::class, ShiftPosition::class)) {
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var shiftPositionMapper: ComponentMapper<ShiftPosition>

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        val shiftPosition = shiftPositionMapper.get(entityId)
        position.x += shiftPosition.x
        position.y += shiftPosition.y
        position.plane += shiftPosition.plane
        shiftPositionMapper.remove(entityId)
    }
}
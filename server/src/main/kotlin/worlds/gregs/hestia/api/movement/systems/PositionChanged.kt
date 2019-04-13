package worlds.gregs.hestia.api.movement.systems

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.services.Aspect
import kotlin.reflect.KClass

/**
 * A base for entity movement position checks
 */
abstract class PositionChanged(vararg classes: KClass<out Component>) : IteratingSystem(Aspect.all(Position::class, Shift::class, *classes)) {

    private lateinit var shiftMapper: ComponentMapper<Shift>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun process(entityId: Int) {
        val position = positionMapper.get(entityId)
        val shift = shiftMapper.get(entityId)
        val newX = getX(position, shift)
        val newY = getY(position, shift)
        val newPlane = getPlane(position, shift)

        if(hasChanged(position, newX, newY, newPlane)) {
            changed(entityId, position, newX, newY, newPlane)
        }
    }

    /**
     * Calculates the next x
     */
    abstract fun getX(position: Position, shift: Shift): Int

    /**
     * Calculates the new y
     */
    abstract fun getY(position: Position, shift: Shift): Int

    /**
     * Calculates the new plane (optional)
     */
    abstract fun getPlane(position: Position, shift: Shift): Int?

    /**
     * Compares the current position with the new values
     */
    abstract fun hasChanged(position: Position, x: Int, y: Int, plane: Int?): Boolean

    /**
     * Handles the position change
     */
    abstract fun changed(entityId: Int, position: Position, x: Int, y: Int, plane: Int?)
}
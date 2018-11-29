package worlds.gregs.hestia.game.plugins.entity.systems.map

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.movement.components.Shift
import worlds.gregs.hestia.services.Aspect
import kotlin.reflect.KClass

/**
 * A base for entity movement position checks
 */
abstract class BasePositionChangeSystem(vararg classes: KClass<out Component>) : IteratingSystem(Aspect.all(ClientIndex::class, Shift::class, *classes)) {

    private lateinit var shiftMapper: ComponentMapper<Shift>
    internal lateinit var positionMapper: ComponentMapper<Position>

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
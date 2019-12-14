package worlds.gregs.hestia.core.world.movement.api.systems

import com.artemis.Component
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import kotlin.reflect.KClass

abstract class RegionChanged(vararg classes: KClass<out Component>) : PositionChanged(*classes) {

    override fun getX(position: Position, shift: Shift): Int {
        return position.x + shift.x
    }

    override fun getY(position: Position, shift: Shift): Int {
        return position.y + shift.y
    }

    /* Returns null as a region is the same for all planes */
    override fun getPlane(position: Position, shift: Shift): Int? {
        return null
    }

    override fun hasChanged(position: Position, x: Int, y: Int, plane: Int?): Boolean {
        return position.regionX != (x shr 6) || position.regionY != (y shr 6)
    }

    override fun changed(entityId: Int, position: Position, x: Int, y: Int, plane: Int?) {
        val oldId = position.regionId
        val newId = Position.regionId(x, y)

        changedRegion(entityId, oldId, newId)
    }

    /**
     * Activates when an entity changes region
     * @param entityId the id of the entity which changed region
     * @param from the region id they were in
     * @param to the region id they are now in
     */
    abstract fun changedRegion(entityId: Int, from: Int, to: Int)
}
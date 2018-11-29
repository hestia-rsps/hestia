package worlds.gregs.hestia.game.plugins.entity.systems.map

import com.artemis.Component
import com.artemis.EntitySubscription
import com.artemis.utils.IntBag
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.movement.components.Shift
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.forEach
import kotlin.reflect.KClass

abstract class EntityRegionChangeSystem(private vararg val classes: KClass<out Component>) : BasePositionChangeSystem(*classes) {

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

    override fun initialize() {
        super.initialize()
        world.aspectSubscriptionManager.get(Aspect.all(ClientIndex::class, *classes)).addSubscriptionListener(object: EntitySubscription.SubscriptionListener {
            override fun inserted(entities: IntBag?) {
                entities?.forEach {entityId ->
                    val position = positionMapper.get(entityId)
                    changedRegion(entityId, -1, position.regionId)
                }
            }

            override fun removed(entities: IntBag?) {
                entities?.forEach {entityId ->
                    val position = positionMapper.get(entityId)
                    changedRegion(entityId, position.regionId, -1)
                }
            }

        })
    }

    /**
     * Activates when an entity changes region
     * @param entityId the id of the entity which changed region
     * @param from the region id they were in
     * @param to the region id they are now in
     */
    abstract fun changedRegion(entityId: Int, from: Int, to: Int)
}
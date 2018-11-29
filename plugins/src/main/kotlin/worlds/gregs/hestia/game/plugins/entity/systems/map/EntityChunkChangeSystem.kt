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

abstract class EntityChunkChangeSystem(private vararg val classes: KClass<out Component>) : BasePositionChangeSystem(*classes) {

    override fun getX(position: Position, shift: Shift): Int {
        return (position.x + shift.x) shr 3
    }

    override fun getY(position: Position, shift: Shift): Int {
        return (position.y + shift.y) shr 3
    }

    override fun getPlane(position: Position, shift: Shift): Int? {
        return position.plane + shift.plane
    }

    override fun hasChanged(position: Position, x: Int, y: Int, plane: Int?): Boolean {
        return position.chunkX != x || position.chunkY != y || position.plane != plane!!
    }

    override fun changed(entityId: Int, position: Position, x: Int, y: Int, plane: Int?) {
        val oldHash = EntityChunkSystem.toHash(position)
        val newHash = EntityChunkSystem.toHash(x, y, plane!!)

        changedChunk(entityId, oldHash, newHash)
    }

    override fun initialize() {
        super.initialize()
        world.aspectSubscriptionManager.get(Aspect.all(ClientIndex::class, *classes)).addSubscriptionListener(object: EntitySubscription.SubscriptionListener {
            override fun inserted(entities: IntBag?) {
                entities?.forEach {entityId ->
                    val position = positionMapper.get(entityId)
                    changedChunk(entityId, -1, EntityChunkSystem.toHash(position))
                }
            }

            override fun removed(entities: IntBag?) {
                entities?.forEach {entityId ->
                    val position = positionMapper.get(entityId)
                    changedChunk(entityId, EntityChunkSystem.toHash(position), -1)
                }
            }

        })
    }

    /**
     * Activates when an entity changes chunk
     * @param entityId the id of the entity which changed
     * @param from the chunk hash they were in
     * @param to the chunk hash they are now in
     */
    abstract fun changedChunk(entityId: Int, from: Int, to: Int)
}
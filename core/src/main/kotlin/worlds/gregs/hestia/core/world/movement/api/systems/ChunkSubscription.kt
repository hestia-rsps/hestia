package worlds.gregs.hestia.core.world.movement.api.systems

import com.artemis.Component
import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.map.model.Chunk.toChunkPosition
import worlds.gregs.hestia.artemis.Aspect
import kotlin.reflect.KClass

abstract class ChunkSubscription(vararg classes: KClass<out Component>) : SubscriptionSystem(Aspect.all(Position::class, *classes)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    override fun inserted(entityId: Int) {
        val position = positionMapper.get(entityId)
        changedChunk(entityId, null, toChunkPosition(position))
    }

    override fun removed(entityId: Int) {
        val position = positionMapper.get(entityId)
        changedChunk(entityId, toChunkPosition(position), null)
    }

    /**
     * Activates when an entity changes chunk
     * @param entityId the id of the entity which changed
     * @param from the chunk hash they were in
     * @param to the chunk hash they are now in
     */
    abstract fun changedChunk(entityId: Int, from: Int?, to: Int?)
}
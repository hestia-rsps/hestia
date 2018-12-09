package worlds.gregs.hestia.game.api.movement

import com.artemis.Component
import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.api.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.core.components.map.Chunk.toChunkPosition
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.services.Aspect
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
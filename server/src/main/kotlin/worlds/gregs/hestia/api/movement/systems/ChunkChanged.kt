package worlds.gregs.hestia.api.movement.systems

import com.artemis.Component
import worlds.gregs.hestia.game.map.Chunk.toChunkPosition
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.movement.components.Shift
import kotlin.reflect.KClass

abstract class ChunkChanged(vararg classes: KClass<out Component>) : PositionChanged(*classes) {

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
        val oldHash = toChunkPosition(position)
        val newHash = toChunkPosition(x, y, plane!!)

        changedChunk(entityId, oldHash, newHash)
    }

    /**
     * Activates when an entity changes chunk
     * @param entityId the id of the entity which changed
     * @param from the chunk hash they were in
     * @param to the chunk hash they are now in
     */
    abstract fun changedChunk(entityId: Int, from: Int, to: Int)
}
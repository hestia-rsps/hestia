package world.gregs.hestia.game.systems.sync

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.movement.ShiftPosition
import world.gregs.hestia.services.Aspect
import kotlin.reflect.KClass

abstract class EntityChunkChangeSystem(type: KClass<out Component>) : IteratingSystem(Aspect.all(ClientIndex::class, type, ShiftPosition::class)) {

    private lateinit var shiftPositionMapper: ComponentMapper<ShiftPosition>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun process(entityId: Int) {
        val shiftPosition = shiftPositionMapper.get(entityId)
        val position = positionMapper.get(entityId)
        val newChunkX = (position.x + shiftPosition.x) shr 3
        val newChunkY = (position.y + shiftPosition.y) shr 3
        val newPlane = position.plane + shiftPosition.plane

        if(position.chunkX != newChunkX || position.chunkY != newChunkY || position.plane != newPlane) {
            val oldHash = EntityChunkSystem.toHash(position)
            val newHash = EntityChunkSystem.toHash(newChunkX, newChunkY, newPlane)

            changedChunk(entityId, oldHash, newHash)
        }
    }

    abstract fun changedChunk(entityId: Int, from: Int, to: Int)
}
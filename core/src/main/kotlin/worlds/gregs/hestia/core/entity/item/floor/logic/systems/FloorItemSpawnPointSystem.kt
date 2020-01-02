package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.logic.creation.FloorItemCreation
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.components.SpawnPoint
import worlds.gregs.hestia.core.entity.item.floor.model.events.CreateFloorItem

/**
 * Processes floor item spawn points
 * 1) waits for spawned floor item to be removed
 * 2) starts count down till next spawn
 * 3) spawns replacement floor item
 */
class FloorItemSpawnPointSystem : IteratingSystem(Aspect.all(SpawnPoint::class, Position::class, Type::class)) {

    private lateinit var spawnPointMapper: ComponentMapper<SpawnPoint>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var amountMapper: ComponentMapper<Amount>

    private lateinit var factory: FloorItemCreation

    override fun inserted(entityId: Int) {
        val spawnPoint = spawnPointMapper.get(entityId)
        spawnPoint.entityId = spawn(entityId)
    }

    override fun process(entityId: Int) {
        val spawnPoint = spawnPointMapper.get(entityId)
        when {
            //If entity not set and count down still active
            spawnPoint.entityId == -1 && spawnPoint.delay > 0 -> {
                //Decrement by one tick
                spawnPoint.delay--
            }
            //When count down complete
            spawnPoint.entityId == -1 && spawnPoint.delay == 0 -> {
                //Spawn a new floor item
                spawnPoint.entityId = spawn(entityId)
                //Reset count down for next spawn
                spawnPoint.delay = spawnPoint.delayTime
            }
        }
    }

    private fun spawn(entityId: Int): Int {
        val position = positionMapper.get(entityId)
        val type = typeMapper.get(entityId)
        val amount = amountMapper.get(entityId)?.amount ?: 1
        return factory.create(CreateFloorItem(type.id, amount, position.x, position.y, position.plane, null, publicTime = -1))
    }
}
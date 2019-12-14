package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.artemis.remove
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.logic.creation.FloorItemCreation
import worlds.gregs.hestia.core.entity.item.floor.logic.creation.FloorItemFactory
import worlds.gregs.hestia.core.entity.item.floor.logic.creation.FloorItemSpawnFactory
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.components.SpawnPoint
import worlds.gregs.hestia.core.entity.item.floor.model.events.CreateFloorItem

@ExtendWith(MockKExtension::class)
internal class FloorItemSpawnPointSystemTest : MockkGame() {
    private var system = FloorItemSpawnPointSystem()

    @SpyK
    private var creation = FloorItemCreation()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, creation)
    }

    @Test
    fun `Entity inserted spawns`() {
        //Given
        create(100, 1, 5, 15, 10, 2)
        //When
        tick()
        //Then
        verify { creation.create(CreateFloorItem(1, 5, 15, 10, 2, null, publicTime = -1)) }
    }

    @Test
    fun `No amount creation returns zero`() {
        //Given
        create(100, 2, null, 15, 10, 2)
        //When
        tick()
        //Then
        verify {
            creation.create(CreateFloorItem(2, 1, 15, 10, 2, null, publicTime = -1))
        }
    }

    @Test
    fun `Start count down when item deleted`() {
        //Given
        val entity = create(100, 1, 5, 15, 10, 2)
        tick()
        val spawnPoint = entity.getComponent(SpawnPoint::class)!!
        //When
        spawnPoint.entityId = -1
        tick()
        //Then
        assertEquals(99, spawnPoint.delay)
        assertEquals(-1, spawnPoint.entityId)
    }

    @Test
    fun `Count down each tick`() {
        //Given
        val entity = create(100, 1, 5, 15, 10, 2)
        tick()
        val spawnPoint = entity.getComponent(SpawnPoint::class)!!
        spawnPoint.entityId = -1
        //When
        tick()
        tick()
        //Then
        assertEquals(98, spawnPoint.delay)
    }

    @Test
    fun `Respawn reset once count down finished`() {
        //Given
        val entity = create(100, 1, 5, 15, 10, 2)
        tick()
        val spawnPoint = entity.getComponent(SpawnPoint::class)!!
        spawnPoint.entityId = -1
        //When
        repeat(100) {
            tick()
        }
        tick()
        //Then
        assertEquals(100, spawnPoint.delay)
        assertNotEquals(-1, spawnPoint.entityId)
    }

    @Test
    fun `Won't count down if entity exists`() {
        //Given
        val entity = create(100, 1, 5, 15, 10, 2)
        tick()
        val spawnPoint = entity.getComponent(SpawnPoint::class)!!
        world.delete(spawnPoint.entityId)
        tick()
        tick()
        //When
        spawnPoint.entityId = 1
        tick()
        //Then
        assertEquals(100, spawnPoint.delay)
    }

    private fun create(delay: Int, type: Int, amount: Int?, x: Int, y: Int, plane: Int = 0): Entity {
        val archetype = FloorItemSpawnFactory().getBuilder().build(world)
        val entity = world.createEntity(archetype)
        entity.getComponent(Type::class)?.id = type
        if (amount == null) {
            entity.edit().remove(Amount::class)
        } else {
            entity.getComponent(Amount::class)?.amount = amount
        }
        entity.getComponent(SpawnPoint::class)?.delay = delay
        entity.getComponent(SpawnPoint::class)?.delayTime = delay
        entity.getComponent(Position::class)?.set(x, y, plane)
        return entity
    }
}